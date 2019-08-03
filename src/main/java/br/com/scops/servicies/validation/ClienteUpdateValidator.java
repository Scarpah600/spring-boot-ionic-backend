package br.com.scops.servicies.validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.scops.dao.ClienteDAO;
import br.com.scops.domain.Cliente;
import br.com.scops.dto.ClienteDTO;
import br.com.scops.resources.exception.FieldMessage;




public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteDAO clidao;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}


	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		List<FieldMessage> list = new ArrayList<>();

		Cliente aux = clidao.findByEmail(objDto.getEmail());
		if(aux !=null && !aux.getId().equals(uriId) ) {
			list.add(new FieldMessage("email","E-mail ja existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}