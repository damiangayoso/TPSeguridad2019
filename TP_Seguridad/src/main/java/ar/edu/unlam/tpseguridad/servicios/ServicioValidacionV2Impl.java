package ar.edu.unlam.tpseguridad.servicios;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tpseguridad.dao.ValidacionV2;

@Service("servicioValidacionV2")
@Transactional
public class ServicioValidacionV2Impl implements ServicioValidacionV2{
	@Inject
	private ValidacionV2 servicioValidacionV2;
	
	@Override
	public boolean passwordVerificar(String p) {
		return servicioValidacionV2.passwordVerificar(p);
	}
}
