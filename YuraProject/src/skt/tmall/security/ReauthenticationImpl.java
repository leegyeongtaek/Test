package skt.tmall.security;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;

import skt.tmall.common.model.process.ProcessException;
import skt.tmall.common.model.security.AbstractReAuthentication;

/**
 * @author leegt80
 *
 */
public class ReauthenticationImpl extends AbstractReAuthentication{

	private Authentication authentication = null;
	
	private Authentication tempAuthentication = null;
	
	@Override
	public String getAuthentication() throws ProcessException {
		
		String name = "";
		
		tempAuthentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(tempAuthentication != null){
			
			authentication = tempAuthentication;
			
			name = authentication.getName();
		} else {
//			throw new ProcessException("authentication is not exist!");
			try 
			{
				name = authentication.getName();
			}
			catch (NullPointerException ex)
			{
				throw new ProcessException("authentication is not exist!");
			}
		}
		
		return name;
	}

	@Override
	public String[] getAuthorities() throws ProcessException {
		
		String[] authorities = null;
		
		if (authentication.getAuthorities() != null) {
			
			String temp_Authority = "";
			int rolePrefix_index  = getRolePrefix().length();
			int authority_count   = authentication.getAuthorities().length;
			
			authorities = new String[authority_count];
			
            for (int i = 0; i < authority_count; i++) {
            	temp_Authority = authentication.getAuthorities()[i].toString();
            	authorities[i] = temp_Authority.substring(rolePrefix_index);
            }
            
        } else {
        	return new String[]{""};
        }
		
		if (!authentication.isAuthenticated()) {
			authentication.setAuthenticated(true);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		return authorities;
	}

}
