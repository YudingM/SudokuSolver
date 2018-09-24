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
        for (int i = 0; i < val; i++) {
            values[i] = 0;
        }
        for (int i = val; i < values.length; i++) {
            values[i] = 0;
        }
    }

    public int getActualVal() {

       return actualVal;

    }

    public void removePossibleVal(int val){

        values[val] = 0;

    }

    public int getPossibleVal(int i) {

        return values[i];

    }

    public int numPossibleValues(){
        int num = 0;
        for (int i = 0; i < values.length; i++) {
            if(values[i] > 0){
                num++;
            }
        }
        return num;
    }

    public boolean isPossibleVal(int i){
        if(values[i] > 0){
            return true;
        }
        return false;
    }

}
