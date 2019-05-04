package com.projects.breakingbook.exposition;

import com.projects.breakingbook.business.service.BookService;
import com.projects.breakingbook.business.service.UserService;
import com.projects.breakingbook.exception.UserNotUpdatedException;
import com.projects.breakingbook.exposition.DTO.FriendDTO;
import com.projects.breakingbook.exposition.DTO.UserDTO;
import com.projects.breakingbook.message.request.LoginForm;
import com.projects.breakingbook.message.request.SignUpForm;
import com.projects.breakingbook.message.response.JwtResponse;
import com.projects.breakingbook.message.response.ResponseMessage;
import com.projects.breakingbook.persistence.entity.Book;
import com.projects.breakingbook.persistence.entity.Friend;
import com.projects.breakingbook.persistence.entity.RoleName;
import com.projects.breakingbook.persistence.entity.User;
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
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private BookService bookService;
    private ModelMapper modelMapper;
    PasswordEncoder encoder;
    JwtProvider jwtProvider;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, BookService bookService, ModelMapper modelMapper, PasswordEncoder encoder, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        String stringRole = signUpRequest.getRole();
        RoleName role;

        if ("admin".equals(stringRole)) {
            role = RoleName.ROLE_ADMIN;
        } else {
            role = RoleName.ROLE_USER;
        }

        user.setRole(role);
        userService.create(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<UserDTO> getAll() {
        List<User> users = this.userService.getAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDTO getOne(@PathVariable final Long id) {
        Optional<User> optionalUser = this.userService.getOne(id);
        return optionalUser.map(this::convertToDTO).orElse(null);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody final UserDTO userDTO) {
        boolean result;
        try {
            System.out.println(convertToEntity(userDTO));
            result = this.userService.update(id, convertToEntity(userDTO));
            System.out.println(result);
            if (result) {
                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            } else {
                throw new UserNotUpdatedException("User not updated");
            }
        } catch(ParseException | UserNotUpdatedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        boolean result = this.userService.delete(id);
        if(result) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not deleted", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/users")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        boolean result = this.userService.deleteAll();
        if(result) {
            return new ResponseEntity<>("All users deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No user deleted", HttpStatus.BAD_REQUEST);
        }
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        if(user.getBooks() != null) {
            List<Long> bookIds = user.getBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList());
            userDTO.setBookIds(bookIds);
        }
        return userDTO;
    }

    private User convertToEntity(UserDTO userDTO) throws ParseException {
        User user = modelMapper.map(userDTO, User.class);
        if(userDTO.getBookIds() != null) {
            List<Book> books = userDTO.getBookIds().stream()
                    .map(id -> this.bookService.getOne(id))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            user.setBooks(books);
        }
        return user;
    }
}
