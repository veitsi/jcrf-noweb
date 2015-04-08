import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Jcrf {
	@XmlElement
	Jcrf jcrf;
	@XmlElement
	ArrayList<Visit> vst = new ArrayList<Visit>();
	
	public Jcrf(){
		super();
	}

 	public static void main(String[] args) {
		Jcrf jcrf = new Jcrf();
		//jcrf.addTestData();

		System.out.println(jcrf.vst);

		// jcrf.toXml();
		 jcrf.fromXml();

		// Jcrf.store(jcrf);
		//Jcrf.load(jcrf);

		System.out.println(jcrf.vst);
	}

	public void toXml() {
		try {
			File file = new File("jcrf.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Jcrf.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); // output
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(this, file);
			jaxbMarshaller.marshal(this, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void fromXml() {
		try {
			File file = new File("jcrf.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Jcrf.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jcrf = (Jcrf) jaxbUnmarshaller.unmarshal(file);
			vst=jcrf.vst;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void addTestData() {
		vst.add(new Visit(1, 1, 366));
		vst.add(new Visit(1, 2, 365));
		vst.add(new Visit(2, 1, 364));
	}
}

class Visit implements Serializable {
	@XmlElement
	int ptn;
	@XmlElement
	int nmr;
	@XmlElement
	static final Date dt = new Date();
	@XmlElement
	int ttr;

	public Visit() {
	}

	public Visit(int ptn, int nmr, int ttr) {
		super();
		if (ttr < 300 || ttr > 420)
			throw new IllegalArgumentException("bad temperature");
		this.ptn = ptn;
		this.nmr = nmr;
		this.ttr = ttr;
	}

	@Override
	public String toString() {
		return "Visit [ptn=" + ptn + ", nmr=" + nmr + ", ttr=" + ttr + ", dt="
				+ dt + "]";
	}

}