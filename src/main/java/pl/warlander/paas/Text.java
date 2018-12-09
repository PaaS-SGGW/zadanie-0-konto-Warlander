/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.warlander.paas;

/**
 *
 * @author Maciej
 */
public class Text {
    
    public final int id;
    public final String text;
    
    public Text() {
        this(-1, "");
    }
    
    public Text(int id, String text) {
        this.id = id;
        this.text = text;
    }
    
    public int getId() {
        return id;
    }
    
    public String getText() {
        return text;
    }
    
}
