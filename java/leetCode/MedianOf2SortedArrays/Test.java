class Test {
    public static void main(String[] args) {
        Solution s= new Solution();
        int[] a = {1,2,3};
        int[] b = {2,3,5};
        double ans = s.findMedianSortedArrays(a,b);
        assert ans == 2.5d;
        System.out.println(ans);    

        int[] c = {1,2,3};
        int[] d = {4,5,6};
        ans = s.findMedianSortedArrays(c,d);
        assert ans == 3.5d;
        System.out.println(ans);        
        
        int[] e = {1,2};
        int[] f = {3,4};
        ans = s.findMedianSortedArrays(e,f);
        assert ans == 2.5d;
        System.out.println(ans); 
        
        int[] g = {0,0,0,0,0};  
        int[] h = {-1,0,0,0,0,0,1};
        ans = s.findMedianSortedArrays(g,h);
        assert ans == 2.5d;
        System.out.println(ans);             
    }
}