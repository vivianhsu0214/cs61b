//homework0
public class HomeworkTester {
	public static void main(String[] args){
		int[] testArray = {-6,3,10,200};
		System.out.println("max by forloop is "+max_forloop(testArray));
		System.out.println("max by whileloop is "+max_whileloop(testArray));
		System.out.println("3 sum equals 0 is " + threeSum(testArray));
		System.out.println("3 distinct sum equals 0 is " + threeSumDistinct(testArray));
	}
	//function max by forloop and whileloop
	private static int max_forloop(int[] a) {
		int max = 0;
		for(int i=0;i<a.length;i++)
			if(a[i]>=max) max = a[i];
		return max;
	}
	private static int max_whileloop(int[] a) {
		int max = 0; int i = 0;
		while(i<a.length) {
			if(a[i]>=max) max = a[i];
			i++;
		}
		return max;
	}
	//3sum solution
	private static boolean threeSum(int[] a) {
		if(a.length < 3) return false;//array size too small
		for(int i=0;i<a.length;i++) {
			for(int j=i;j<a.length;j++) {
				for(int k=j;k<a.length;k++) {
					if(a[i]+a[j]+a[k] == 0) return true;
				}
			}
		}
		return false;
	}
	//3sum-distinct solution
	private static boolean threeSumDistinct(int[] a) {
		if(a.length < 3) return false;//array size too small
		for(int i=0;i<a.length;i++) {
			for(int j=i+1;j<a.length-1;j++) {
				for(int k=j+1;k<a.length-2;k++) {
					if(a[i]+a[j]+a[k] == 0) return true;
				}
			}
		}
		return false;
	}
}
