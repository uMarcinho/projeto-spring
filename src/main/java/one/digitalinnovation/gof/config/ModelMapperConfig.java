package one.digitalinnovation.gof.config;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.dto.ClienteInputDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.createTypeMap(ClienteInputDTO.class, Cliente.class)
            .addMappings(mapper -> {
                mapper.skip(Cliente::setId);
                mapper.skip(Cliente::setEndereco);
            });

        return modelMapper;
    }
}