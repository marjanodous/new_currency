package com.example.currencyconvertnew;
/*support telgram id =@javaprogrammer_eh
 * 04/02/1398
 * creted by elmira hossein zadeh*/
public class StoreList {
    private String id;
    private String name;

    public StoreList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    // string in spinner
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StoreList){
            StoreList c = (StoreList)obj;
            return c.getName().equals(name) && c.getId() == id;
        }

        return false;
    }
}

