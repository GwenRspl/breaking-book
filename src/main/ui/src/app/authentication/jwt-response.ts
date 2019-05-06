export interface JwtResponse {
  token: string;
  type: string;
  username: string;
  authorities: string[];
}
