public class Cell {

    private int[] values = new int[9];
    private int actualVal = 0;

    public Cell(){
        for(int i = 0; i < values.length; i++){
            values[i] = 1;
        }
    }

    public void setActualVal(int val){
        if(val == 0){
            actualVal = 0;
        }
        else {
            actualVal = val;
            for (int i = 0; i < val - 1; i++) {
                values[i] = 0;
            }
            for (int i = val; i < values.length; i++) {
                values[i] = 0;
            }
        }
    }

    public int getActualVal() {

       return actualVal;

    }

    public void removePossibleVal(int val){
        if(val == actualVal)
            values[val - 1] = 1;
        else
            values[val - 1] = 0;
    }

    public int numPossibleValues() {
        int sum = 0;

        for(int i: values){
            if(i == 1)
               sum++;
        }
        return sum;
    }


    public int getPossibleVal(int i){
        return values[i];
    }

    public void setOnlyPossibleVal(){
        for (int i = 0; i < values.length; i++) {
            if(values[i] > 1){
                setActualVal(i + 1);
            }
        }

    }

}
