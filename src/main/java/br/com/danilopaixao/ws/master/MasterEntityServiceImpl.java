package br.com.danilopaixao.ws.master;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.detail.DetailEntity;
import br.com.danilopaixao.ws.detail.DetailEntityResponse;
import br.com.danilopaixao.ws.user.User;
import br.com.danilopaixao.ws.user.UserService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
class MasterEntityServiceImpl implements MasterEntityService {

	@Autowired
    private MasterEntityRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public MasterEntityResponse save(MasterEntityRequest processRequest) {
		log.info("save process => " + processRequest);
		MasterEntity master = MasterEntity.builder()
				.code(processRequest.getCode())
				.summary(processRequest.getSummary())
				.description(processRequest.getDescription())
				.details(processRequest.getDetailEntityRequests()
								.stream()
								.map(
									l->DetailEntity
											.builder()
											.description(l.getDescription())
											.build()
								).collect(Collectors.toList())
				).build();
		master = this.repository.save(master);
		final MasterEntity idMaster = master;
		List<DetailEntity> legalAdvices = processRequest.getDetailEntityRequests()
				.stream()
				.map(
					l->DetailEntity
							.builder()
							.master(idMaster)
							.description(l.getDescription())
							.build()
				).collect(Collectors.toList());
		master.setDetails(legalAdvices);
		this.repository.save(master);
		return MasterEntityResponse
					.builder()
					.id(master.getId())
					.code(master.getCode())
					.summary(master.getSummary())
					.description(master.getDescription())
					.build();
		
	}
	
	private User getUser(Long id) {
		return userService.getUserById(id);
	}
	
	@Override
	public MasterEntityResponse save(Long id, MasterEntityRequest processRequest) {
		log.info("save process", processRequest);
		MasterEntity process = this.repository.findOne(id);
		process.setSummary(processRequest.getSummary());
		process.setCode(processRequest.getCode());
		process.setDescription(processRequest.getDescription());
		this.repository.save(process);
		return MasterEntityResponse
					.builder()
					.id(process.getId())
					.code(process.getCode())
					.summary(process.getSummary())
					.description(process.getDescription())
					.build();
		
	}
	
	@Override
	public MasterEntityResponse getById(Long id) {
		MasterEntity master = this.repository.findOne(id);
		return MasterEntityResponse.builder()
				.id(master.getId())
				.code(master.getCode())
				.summary(master.getSummary())
				.description(master.getDescription())
				.details(master.getDetails()
						.stream()
						.map(
							l->DetailEntityResponse
									.builder()
									.id(l.getId())
									.description(l.getDescription())
									.build()
						).collect(Collectors.toList())
				).build();
	}
	
	@Override
	public List<MasterEntityResponse> getByAllMasterEntity() {
		return this.repository
				.findAll()
				.stream()
				.map(p -> MasterEntityResponse.builder()
								.id(p.getId())
								.code(p.getCode())
								.summary(p.getSummary())
								.description(p.getDescription())
								.details(p.getDetails()
										.stream()
										.map(
											l->DetailEntityResponse
													.builder()
													.id(l.getId())
													.description(l.getDescription())
													.build()
										).collect(Collectors.toList())
								).build()
				).collect(Collectors.toList());		
	}

	@Override
	public MasterEntity getMasterEntityById(Long id) {
		return repository.getOne(id);
	}
	
}
