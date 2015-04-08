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
public class Jcrf implements Serializable {
	@XmlElement
	private static final long serialVersionUID = -517189538141551534L;

	@XmlElement
	ArrayList<Visit> vst = new ArrayList<Visit>();

	public static void main(String[] args) {
		Jcrf jcrf = new Jcrf();
		// Jcrf.addTestData(jcrf);

		System.out.println(jcrf.vst);

		// Jcrf.toXml(jcrf);
		// Jcrf.fromXml(jcrf);

		// Jcrf.store(jcrf);
		Jcrf.load(jcrf);

		System.out.println(jcrf.vst);
	}

	public static void toXml(Jcrf jcrf) {
		try {
			File file = new File("file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Jcrf.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); // output
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(jcrf, file);
			jaxbMarshaller.marshal(jcrf, System.out);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void fromXml(Jcrf jcrf) {
		try {
			File file = new File("file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Jcrf.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jcrf = (Jcrf) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void addTestData(Jcrf jcrf) {
		jcrf.vst.add(new Visit(1, 1, 366));
		jcrf.vst.add(new Visit(1, 2, 365));
		jcrf.vst.add(new Visit(2, 1, 364));
	}

	public static void store(Jcrf jcrf) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("temp.out");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(jcrf);
			oos.flush();
			oos.close();
			System.out.println("jcrf is OK!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void load(Jcrf jcrf) {
		FileInputStream fis;
		try {
			fis = new FileInputStream("temp.out");
			ObjectInputStream oin = new ObjectInputStream(fis);
			jcrf = (Jcrf) oin.readObject();
			oin.close();

		} 
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("in load "+jcrf.vst);
	}

}

class Visit implements Serializable {
	@XmlElement
	private static final long serialVersionUID = 2871028816981853938L;
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