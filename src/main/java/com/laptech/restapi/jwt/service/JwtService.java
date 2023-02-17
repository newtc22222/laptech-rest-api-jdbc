package com.laptech.restapi.jwt.service;

import com.laptech.restapi.common.exception.InternalServerErrorException;
import com.laptech.restapi.common.exception.ResourceNotFoundException;
import com.laptech.restapi.common.exception.TokenInvalidException;
import com.laptech.restapi.dao.RefreshTokenDAO;
import com.laptech.restapi.dao.RoleDAO;
import com.laptech.restapi.dao.UserDAO;
import com.laptech.restapi.jwt.dto.JwtRequest;
import com.laptech.restapi.jwt.dto.JwtResponse;
import com.laptech.restapi.jwt.dto.TokenRefreshResponse;
import com.laptech.restapi.jwt.util.JwtUtil;
import com.laptech.restapi.model.RefreshToken;
import com.laptech.restapi.model.Role;
import com.laptech.restapi.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @since 2023-01-04
 */
@Log4j2
@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private RefreshTokenDAO refreshTokenDAO;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) {
        String phone = jwtRequest.getPhone();
        String password = jwtRequest.getPassword();
        authenticate(phone, password);

        final UserDetails userDetails = loadUserByUsername(phone); // check user here so code below will be in good case
        String generateNewToken = jwtUtil.generateJwtAccessToken(userDetails);

        User user = userDAO.findUserByPhone(phone);
        List<Role> roleList = roleDAO.findRoleByUserId(user.getId());

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setCode(UUID.randomUUID().toString());
        refreshToken.setUserId(user.getId());
        if (refreshTokenDAO.insert(refreshToken) == 0) {
            throw new InternalServerErrorException("[Error] Cannot insert refresh token to database!");
        }
        return new JwtResponse(user, roleList, generateNewToken, refreshToken.getCode());
    }

    public TokenRefreshResponse refreshJwtToken(String refreshToken) {
        RefreshToken token = refreshTokenDAO.findRefreshTokenByCode(refreshToken);
        if (token == null) {
            throw new TokenInvalidException("Refresh token isn't exited in system!");
        } else if (token.getExpiredDate().compareTo(LocalDateTime.now()) < 0) { // rf expired
            throw new TokenInvalidException("Refresh token was expired! Please login again!");
        }

        User user = userDAO.findById(token.getUserId());

        final UserDetails userDetails = loadUserByUsername(user.getPhone());
        String generateNewToken = jwtUtil.generateJwtAccessToken(userDetails);
        return new TokenRefreshResponse(generateNewToken, refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String userPhone) throws UsernameNotFoundException {
        User user = userDAO.findUserByPhone(userPhone);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getPhone(),
                    user.getPassword(),
                    getRoleOfUser(user)
            );
        } else {
            log.info("[FIND USER] {} is not existed in system!", userPhone);
            throw new ResourceNotFoundException("[Info] Your account has incorrect field! Please check your input again!");
//            throw new UsernameNotFoundException("User phone are not valid");
        }
    }

    private Set<GrantedAuthority> getRoleOfUser(User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        List<Role> roleList = roleDAO.findRoleByUserId(user.getId());
        roleList.forEach(role -> grantedAuthorities.add(
                new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())
        ));
        return grantedAuthorities;
    }

    private void authenticate(String phone, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        } catch (DisabledException | BadCredentialsException err) {
            log.info("[AUTHENTICATE] {}", err.getLocalizedMessage());
            throw new RuntimeException(err);
        }
    }
}
