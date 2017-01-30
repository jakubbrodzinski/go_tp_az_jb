package models.Commands;

/**
 * Created by jakub on 1/27/17.
 */
public class EndProposition{
	private String endprop;

	public EndProposition(String endprop) {
		this.endprop = endprop;
	}

	public String toString(){
		return endprop;
	}

	public String getEndprop() {
		return endprop;
	}
}

