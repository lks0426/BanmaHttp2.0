package com.banma_school.http;


public enum ContentType {
	CSS("CSS"), 
	GIF("GIF"), 
	HTM("HTM"), 
	HTML("HTML"), 
	ICO("ICO"), 
	JPG("JPG"), 
	JPEG("JPEG"), 
	PNG("PNG"),
	TXT("TXT"), 
	XML("XML");

	private final String extension;

	ContentType(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		switch (this) {
			case CSS:
				return "Content-Type: text/css charset=" + Constant.UTF_8;
			case GIF:
				return "Content-Type: image/gif";
			case HTM:
			case HTML:
				return "Content-Type: text/html charset=" + Constant.UTF_8;
			case ICO:
				return "Content-Type: image/gif";
			case JPG:
			case JPEG:
				return "Content-Type: image/jpeg";
			case PNG:
				return "Content-Type: image/png";
			case TXT:
				return "Content-type: text/plain charset=" + Constant.UTF_8;
			case XML:
				return "Content-type: text/xml charset=" + Constant.UTF_8;
			default:
				return null;
		}
	}
}
