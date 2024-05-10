package com.ennov.ticketapi.config;

import com.ennov.ticketapi.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;


@RequiredArgsConstructor
public class AuditorResolver implements AuditorAware<String> {

    @Override
    @Transactional(readOnly = true)
    public Optional<String> getCurrentAuditor() {

        String username = SecurityUtils.getCurrentUsername();
        if (!StringUtils.hasLength(username)) {
            return Optional.ofNullable("Super");
        }

        return Optional.ofNullable(username);
    }
}
