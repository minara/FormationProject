package com.excilys.computerdatabase.mapper;

import org.joda.time.LocalDate;

import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.om.Company;
import com.excilys.computerdatabase.om.Computer;

public class ComputerMapper {

	public ComputerMapper() {
	}

	public static Computer fromDTO(ComputerDTO computerDTO) {
		Computer computer = new Computer();
		
		if (computerDTO.getId() > 0)
			computer.setId(computerDTO.getId());
		if (computerDTO.getName() != null && computerDTO.getName() != "")
			computer.setName(computerDTO.getName());
		if (computerDTO.getIntroduced() != null
				&& computerDTO.getIntroduced() != "")
			computer.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()));
		if (computerDTO.getDiscontinued() != null
				&& computerDTO.getDiscontinued() != "")
			computer.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued()));
		if (computerDTO.getCompanyId() > 0) {
			Company c = Company.builder().id(computerDTO.getCompanyId())
					.build();
			computer.setCompany(c);
		}
		return computer;
	}

	public static ComputerDTO toDTO(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();

		if (computer.getId() > 0)
			computerDTO.setId(computer.getId());
		if (computer.getName() != null)
			computerDTO.setName(computer.getName());
		if (computer.getIntroduced() != null)
			computerDTO.setIntroduced(computer.getIntroduced().toString());
		if (computer.getDiscontinued() != null)
			computerDTO.setDiscontinued(computer.getDiscontinued().toString());
		if (computer.getCompany() != null)
			computerDTO.setCompanyId(computer.getCompany().getId());
		return computerDTO;
	}

}
