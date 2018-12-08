package br.com.danilopaixao.ws.detail;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.danilopaixao.ws.master.MasterEntity;
import br.com.danilopaixao.ws.master.MasterEntityService;
import br.com.danilopaixao.ws.user.User;
import br.com.danilopaixao.ws.user.UserService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@Transactional
class DetailEntityServiceImpl implements DetailEntityService {

	@Autowired
    private DetailEntityRepository repository;
	
	@Autowired
	private MasterEntityService masterEntityService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public DetailEntityResponse save(DetailEntityRequest detailEntityRequest) {
		log.info("save detail => " + detailEntityRequest);
		final MasterEntity master = masterEntityService.getMasterEntityById(Long.valueOf(detailEntityRequest.getMasterId()));
		DetailEntity legalAdvice = DetailEntity.builder()
				.master(master)
				.description(detailEntityRequest.getDescription())
				.build();
		legalAdvice = this.repository.save(legalAdvice);	
		return DetailEntityResponse
				.builder()
				.id(legalAdvice.getId())
				.masterId(legalAdvice.getMaster().getId())
				.description(legalAdvice.getDescription())
				.build();
	}
	
	private User getUser(Long id) {
		return userService.getUserById(id);
	}
	
	@Override
	public DetailEntityResponse save(Long id, DetailEntityRequest detailRequest) {
		log.info("save detail", detailRequest);
		final MasterEntity master = masterEntityService.getMasterEntityById(Long.valueOf(detailRequest.getMasterId()));
		
		DetailEntity detail = this.repository.findOne(id);
		detail.setMaster(master);
		detail.setDescription(detailRequest.getDescription());
		this.repository.save(detail);
		return DetailEntityResponse
					.builder()
					.id(detail.getId())
					.masterId(detail.getMaster().getId())
					.description(detail.getDescription())
					.build();
	}
	
	@Override
	public DetailEntityResponse getById(Long id) {
		DetailEntity legalAdvice = this.repository.findOne(id);
		return DetailEntityResponse.builder()
				.id(legalAdvice.getId())
				.masterId(legalAdvice.getMaster().getId())
				.description(legalAdvice.getDescription())
				.build();
	}
	
	@Override
	public List<DetailEntityResponse> getAllDetailEntity() {
		return this.repository
				.findAll()
				.stream()
				.map(l -> DetailEntityResponse.builder()
								.id(l.getId())
								.masterId(l.getMaster().getId())
								.description(l.getDescription())
								.build()
				).collect(Collectors.toList());		
	}
	
}
