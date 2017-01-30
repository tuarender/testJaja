package javax.topgun.common;

public class ErrorBean {
    
    public ErrorBean(String detail){
	this.detail = detail;
    }
    
    private String detail;

    /**
     * @return the detail
     */
    public String getDetail() {
	return detail;
    }

    /**
     * @param detail
     *            the detail to set
     */
    public void setDetail(String detail) {
	this.detail = detail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ErrorBean [detail=");
	builder.append(detail);
	builder.append("]");
	return builder.toString();
    }
}
