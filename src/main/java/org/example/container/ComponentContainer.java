package org.example.container;

import lombok.Getter;
import lombok.Setter;
import org.example.dto.ProfileDTO;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ComponentContainer {
    private ProfileDTO currentProfile;
}
