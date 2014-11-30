package project.shared;

import java.io.Serializable;

public class TransmissionPackage implements Serializable {
	public final Code code;
	public final Object payload;
	private static final long serialVersionUID = 7526471155612776147L;
	
	public TransmissionPackage (Code code, Object payload) {
		this.code = code;
		this.payload = payload;
	}
}
