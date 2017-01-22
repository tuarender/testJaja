package javax.topgun.competency;

public class CheckUpHobby
{
	private int idUser;
    private int idHobby;   
    private int idGroup;   
    private int hobbyOrder ;
    private String othHobby ;   
    private int skill ;
    
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public int getIdHobby() 
	{
		return idHobby;
	}
	public void setIdHobby(int idHobby) 
	{
		this.idHobby = idHobby;
	}
	public int getIdGroup() 
	{
		return idGroup;
	}
	public void setIdGroup(int idGroup) 
	{
		this.idGroup = idGroup;
	}
	public int getHobbyOrder() 
	{
		return hobbyOrder;
	}
	public void setHobbyOrder(int hobbyOrder) 
	{
		this.hobbyOrder = hobbyOrder;
	}
	public String getOthHobby() 
	{
		return othHobby != null ? othHobby : "";
	}
	public void setOthHobby(String othHobby) 
	{
		this.othHobby = othHobby != null ? othHobby : "";
	}
	public int getSkill() 
	{
		return skill;
	}
	public void setSkill(int skill) 
	{
		this.skill = skill;
	}
}
