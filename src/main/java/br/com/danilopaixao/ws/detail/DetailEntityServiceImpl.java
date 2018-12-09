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
	public DetailResponse save(DetailRequest detailEntityRequest) {
		log.info("save detail => " + detailEntityRequest);
		final MasterEntity master = masterEntityService.getMasterEntityById(Long.valueOf(detailEntityRequest.getMasterId()));
		DetailEntity legalAdvice = DetailEntity.builder()
				.master(master)
				.description(detailEntityRequest.getDescription())
				.build();
		legalAdvice = this.repository.save(legalAdvice);	
		return DetailResponse
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
	public DetailResponse save(Long id, DetailRequest detailRequest) {
		log.info("save detail", detailRequest);
		final MasterEntity master = masterEntityService.getMasterEntityById(Long.valueOf(detailRequest.getMasterId()));
		
		DetailEntity detail = this.repository.findOne(id);
		detail.setMaster(master);
		detail.setDescription(detailRequest.getDescription());
		this.repository.save(detail);
		return DetailResponse
					.builder()
					.id(detail.getId())
					.masterId(detail.getMaster().getId())
					.description(detail.getDescription())
					.build();
	}
	
	@Override
	public DetailResponse getById(Long id) {
		DetailEntity legalAdvice = this.repository.findOne(id);
		return DetailResponse.builder()
				.id(legalAdvice.getId())
				.masterId(legalAdvice.getMaster().getId())
				.description(legalAdvice.getDescription())
				.build();
	}
	
	@Override
	public List<DetailResponse> getAllDetailEntity() {
		return this.repository
				.findAll()
				.stream()
				.map(l -> DetailResponse.builder()
								.id(l.getId())
								.masterId(l.getMaster().getId())
								.description(l.getDescription())
								.build()
				).collect(Collectors.toList());		
	}
	
}
