/**
 * Created by Мария on 14.04.2016.
 */
public class TermCrutch
{
	private int fakeId;
	private int realId;

	TermCrutch(int fake, int real)
	{
		fakeId = fake;
		realId = real;
	}

	public int getFakeId(){ return fakeId; }

	public int getRealId(){ return realId; }

	public String toString(){
		return fakeId + " " + realId +"\n";
	}
}
