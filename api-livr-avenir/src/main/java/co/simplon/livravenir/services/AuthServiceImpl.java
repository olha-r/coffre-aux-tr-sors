package co.simplon.livravenir.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import co.simplon.livravenir.dtos.Credentials;
import co.simplon.livravenir.dtos.TokenInfo;
import co.simplon.livravenir.entities.Account;
import co.simplon.livravenir.repositories.AuthRepository;
import co.simplon.livravenir.utils.AuthHelper;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthHelper authHelper;

    private final AuthRepository authRepo;

    public AuthServiceImpl(AuthHelper authHelper,
	    AuthRepository authRepo) {
	this.authHelper = authHelper;
	this.authRepo = authRepo;
    }

    @Override
    public void signUp(Credentials inputs) {
	Account account = new Account();
	account.setIdentifier(inputs.getIdentifier());

	String hashPassword = authHelper
		.encode(inputs.getPassword());
	account.setPassword(hashPassword);

	// List<String> roles = new ArrayList<String>();
	// roles.add("ROLE_ADMIN");
	// account.setRoles(roles);

	authRepo.save(account);

    }

    @Override
    public TokenInfo signIn(Credentials inputs) {
	String identifier = inputs.getIdentifier();
	String candidate = inputs.getPassword();
	Account account = authRepo
		.getByIdentifier(identifier);
	if (account != null) {
	    boolean match = authHelper.matches(candidate,
		    account.getPassword());
	    if (match) {
		String name = account.getIdentifier();
		// List<String> roles = account.getRoles();
		String token = authHelper.createJWT(null,
			name);

		TokenInfo tokenInfo = new TokenInfo();
		tokenInfo.setToken(token);
		// tokenInfo.setRoles(roles);
		return tokenInfo;
	    } else {
		throw new BadCredentialsException(
			"Wrong credentials");
	    }
	} else {
	    throw new BadCredentialsException(
		    "Wrong credentials");
	}

    }

}
