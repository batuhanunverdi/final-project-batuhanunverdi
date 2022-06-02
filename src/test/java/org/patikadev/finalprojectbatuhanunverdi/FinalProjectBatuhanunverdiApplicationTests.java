package org.patikadev.finalprojectbatuhanunverdi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.patikadev.finalprojectbatuhanunverdi.entity.Account;
import org.patikadev.finalprojectbatuhanunverdi.entity.User;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountCurrency;
import org.patikadev.finalprojectbatuhanunverdi.entity.enums.AccountType;
import org.patikadev.finalprojectbatuhanunverdi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
class FinalProjectBatuhanunverdiApplicationTests {
	@Autowired
	public UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Test
	void should_match_password_with_bcryptPasswordEncoder(){
		String password = "password";
		String encodedPassword = passwordEncoder.encode(password);
		Assertions.assertThat(encodedPassword).isNotEmpty();
		Assertions.assertThat(passwordEncoder.matches(password,encodedPassword)).isTrue();

	}

	@Test
	void should_create_success_user(){
		User user = new User();
		user.setEmail("mertbatuhanu@hotmail.com");
		user.setPassword("password");
		user.setTc("37012561724");
		Account demandDepositAccount = new Account();
		demandDepositAccount.setAccountType(AccountType.DemandDeposit);
		demandDepositAccount.setUser(user);
		demandDepositAccount.setIBAN("TR1234");
		demandDepositAccount.setAccountCurrency(AccountCurrency.TL);
		user.setAccount(Set.of(demandDepositAccount));
		userRepository.save(user);
		Assertions.assertThat(user.getId()).isNotNull();
		Optional<User> optionalUser = userRepository.findById(user.getId());
		Assertions.assertThat(optionalUser).isPresent();
		User u = optionalUser.get();
		Assertions.assertThat(u.getTc()).isNotEmpty();
		Assertions.assertThat(u.getTc()).isEqualTo("37012561724");
		Assertions.assertThat(u.getEmail()).isNotEmpty();
		Assertions.assertThat(u.getEmail()).isEqualTo("mertbatuhanu@hotmail.com");
		Assertions.assertThat(u.getPassword()).isNotEmpty();
		Assertions.assertThat(u.getPassword()).isEqualTo("password");
	}



}
