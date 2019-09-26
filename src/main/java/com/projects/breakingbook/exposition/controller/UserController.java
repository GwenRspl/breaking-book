package com.projects.breakingbook.exposition.controller;

import com.projects.breakingbook.business.entity.Book;
import com.projects.breakingbook.business.entity.RoleName;
import com.projects.breakingbook.business.entity.User;
import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.exposition.DTO.UserDTO;
import com.projects.breakingbook.exposition.exception.UserNotUpdatedException;
import com.projects.breakingbook.exposition.message.request.LoginForm;
import com.projects.breakingbook.exposition.message.request.SignUpForm;
import com.projects.breakingbook.exposition.message.response.JwtResponse;
import com.projects.breakingbook.exposition.message.response.ResponseMessage;
import com.projects.breakingbook.security.jwt.JwtProvider;
import com.projects.breakingbook.security.services.UserPrinciple;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${breaking-book.app.client}")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BookService bookService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(final AuthenticationManager authenticationManager, final UserService userService, final BookService bookService, final ModelMapper modelMapper, final PasswordEncoder encoder, final JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final LoginForm loginRequest) {

        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String jwt = this.jwtProvider.generateJwtToken(authentication);
        final UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody final SignUpForm signUpRequest) {
        if (this.userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (this.userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        final User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                this.encoder.encode(signUpRequest.getPassword()));

        final String stringRole = signUpRequest.getRole();
        final RoleName role;

        if ("admin".equals(stringRole)) {
            role = RoleName.ROLE_ADMIN;
        } else {
            role = RoleName.ROLE_USER;
        }

        user.setRole(role);
        this.userService.create(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserDTO> getAll() {
        final List<User> users = this.userService.getAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/username/{username}")
    public UserDTO getOneByUsername(@PathVariable final String username) {
        final Optional<User> optionalUser = this.userService.findUserByUsername(username);
        return optionalUser.map(this::convertToDTO).orElse(null);
    }

    @GetMapping("/users/{id}")
    public UserDTO getOne(@PathVariable final Long id) {
        final Optional<User> optionalUser = this.userService.getOne(id);
        return optionalUser.map(this::convertToDTO).orElse(null);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final UserDTO userDTO) {
        final boolean result;
        try {
            result = this.userService.update(id, this.convertToEntity(userDTO));
            if (result) {
                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            } else {
                throw new UserNotUpdatedException("User not updated");
            }
        } catch (final UserNotUpdatedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final boolean result = this.userService.delete(id);
        if (result) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    private UserDTO convertToDTO(final User user) {
        final UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
        if (user.getBooks() != null) {
            final List<Long> bookIds = user.getBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            userDTO.setBookIds(bookIds);
        }
        return userDTO;
    }

    private User convertToEntity(final UserDTO userDTO) {
        final User user = this.modelMapper.map(userDTO, User.class);
        if (userDTO.getBookIds() != null) {
            final List<Book> books = userDTO.getBookIds().stream()
                    .map(this.bookService::getOne)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            user.setBooks(books);
        }
        return user;
    }
}
