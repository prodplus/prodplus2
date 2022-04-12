package br.com.prodplus.models.auxiliares;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Marlon Fernando Garcia
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganoNode implements Serializable {

	private static final long serialVersionUID = -6415195887924004599L;
	
	private String name;
	private String type;
	private Set<OrganoNode> children = new HashSet<>();

}
