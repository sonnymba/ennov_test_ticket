package com.ennov.ticketapi.component;

import com.ennov.ticketapi.dao.PrivilegeRepository;
import com.ennov.ticketapi.dao.RoleRepository;
import com.ennov.ticketapi.dao.UserRepository;
import com.ennov.ticketapi.entities.Privilege;
import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component()
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    public static final String USERNAME = "admin";
    public static final String EMAIL = "admin@test.com";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String READ_PRIVILEGE = "READ_PRIVILEGE";
    public static final String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";

    private  boolean alreadySetup = false;

    public boolean isAlreadySetup() {
        return alreadySetup;
    }

    public void setAlreadySetup(boolean alreadySetup) {
        this.alreadySetup = alreadySetup;
    }


    private final UserRepository userRepository;

    @Value("${user.secret}")
    private String secret;

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;

    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(userRepository.findByEmail(EMAIL).isPresent()){
            setAlreadySetup(true);
        }


        if (isAlreadySetup())
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound(READ_PRIVILEGE);
        Privilege writePrivilege = createPrivilegeIfNotFound(WRITE_PRIVILEGE);

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound(ROLE_ADMIN, adminPrivileges);
        createRoleIfNotFound(ROLE_USER, Collections.singletonList(readPrivilege));

        Role adminRole = roleRepository.findByName(ROLE_ADMIN);
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(passwordEncoder.encode(secret));
        user.setEmail(EMAIL);
        user.setRoles(Collections.singletonList(adminRole));
        user.setEnabled(true);
        user.setDefaultUser(true);
        userRepository.save(user);

        setAlreadySetup(true);
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        return privilegeRepository.findByName(name).orElse(
                privilegeRepository.save(new Privilege(name))
        );

    }

    @Transactional
    void createRoleIfNotFound(String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }
}
