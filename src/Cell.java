public class Cell {

    int[] values = new int[9];
    int actualVal = 0;

    public Cell(){
        for(int i = 0; i < values.length; i++){
            values[i] = 1;
        }
    }

    public void setActualVal(int val){
        actualVal = val;
    }

    public void removeValue(int val){
        values[val] = 0;
    }

    public int returnActualVal(){
        return actualVal;
    }
}
