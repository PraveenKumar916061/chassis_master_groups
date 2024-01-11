package com.imag.master_groups.webcontroller;

import com.imag.master_groups.dto.AuthRequest;
import com.imag.master_groups.dto.AuthResponse;
import com.imag.master_groups.dto.RefreshTokenResponse;
import com.imag.master_groups.dto.UserDto;
import com.imag.master_groups.exception.UserDefinedException;
import com.imag.master_groups.model.RefreshToken;
import com.imag.master_groups.model.User;
import com.imag.master_groups.security.JwtService;
import com.imag.master_groups.security.RefreshService;
import com.imag.master_groups.service.OrganizationMemberService;
import com.imag.master_groups.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private OrganizationMemberService orgMemberService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshService refreshService;

    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public User addUser(@RequestBody User user) throws UserDefinedException {
        return userService.addUser(user);
    }

    @PutMapping("/update-user/{user-id}")
    public User updateUserDetails(@PathVariable("user-id") String userId, @RequestBody UserDto userDto) throws UserDefinedException {
        return userService.updateUserDetails(userId.toUpperCase(), userDto);
    }

    @GetMapping("/users")
    public List<User> getUsers() throws UserDefinedException {
        return userService.getUsers();
    }

    @DeleteMapping("/remove-user/{user-id}")
    public String removeUser(@PathVariable("user-id") String userId) throws UserDefinedException {
        return userService.removeUser(userId.toUpperCase());
    }

    @DeleteMapping("/delete-user/{user-id}")
    public String deleteUser(@PathVariable("user-id") String userId) throws UserDefinedException {
        return userService.deleteUser(userId.toUpperCase());
    }

    @PostMapping("/login")
    public ResponseEntity authenticateAndGetToken(@RequestBody @Valid AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername().toUpperCase(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername().toUpperCase());
                String userId = userService.getUserIdByUsername(authRequest.getUsername().toUpperCase());
                String refreshToken = refreshService.searchUserRefreshToken(userId);
                return ResponseEntity.status(HttpStatus.OK).body(AuthResponse.builder().organizationMember(orgMemberService.organizationMember(authRequest.getUsername().toUpperCase())).refreshToken(refreshToken).token(token).build());
            } else
                throw new UserDefinedException("Authentication failed");
        } catch (UsernameNotFoundException e) {
            throw new UserDefinedException("Invalid username...!");
        } catch (BadCredentialsException e) {
            throw new UserDefinedException("Bad credentials...!");
        }
    }


    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(@RequestParam String refreshtoken) {
        RefreshToken refreshToken = refreshService.searchRefreshToken(refreshtoken);
        String username = refreshToken.getUser().getEmail();
        RefreshToken refreshToken1 = refreshService.verifyExpiration(refreshToken);
        return RefreshTokenResponse.builder().token(jwtService.generateToken(username)).refreshToken(refreshToken1.getToken()).build();
    }

}
