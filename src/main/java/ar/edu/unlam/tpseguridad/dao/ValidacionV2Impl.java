package ar.edu.unlam.tpseguridad.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tpseguridad.modelo.Registro;
import ar.edu.unlam.tpseguridad.modelo.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@Repository("ValidacionV2")
public class ValidacionV2Impl implements ValidacionV2{
ArrayList<String> lista = new ArrayList<String>();
String[] commonPass = new String[] {"123456","password","123456789","12345678","12345","111111","1234567","sunshine","qwerty","iloveyou","princess","admin","welcome","666666","abc123","football","123123","monkey","654321","!@#$%^&*","charlie","aa123456","donald","password1","qwerty123",};

	@Override
	public boolean passwordVerificar(String p) {
		lista.addAll(Arrays.asList(commonPass));
		if(p.matches("[a-zA-Z0-9]{12,128}")) {
			
			for(int x=0;x<lista.size();x++) {
				  if(lista.get(x).equals(p)) {
					  return false;
				  }
			}
			
			return true;
		}
		return false;
	}
	
}
