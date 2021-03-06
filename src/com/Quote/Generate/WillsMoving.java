package com.Quote.Generate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WillsMoving {

    public final float PRICE_PACKAGE_A = 100;
    public final float PRICE_PACKAGE_B = 150;
    public final float PRICE_STORAGE_SMALL = 8;
    public final float PRICE_Storage_LARGE = 20.11f;
    public final float PRICE_BOX_SMALL = 2.50f;
    public final float PRICE_BOX_LARGE = 4.50f;

    private final MovingElement packageA, packageB, boxSmall, boxLarge, storageSmall, storageLarge;

    public WillsMoving(){
        packageA = new MovingElement("Package A", PRICE_PACKAGE_A, "hr", "Enter number of hours");
        packageB = new MovingElement("Package B", PRICE_PACKAGE_B, "hr", "Enter number of hours");
        storageLarge = new MovingElement("Large Storage", PRICE_Storage_LARGE, "day", "Enter days");
        storageSmall = new MovingElement("Small Storage", PRICE_STORAGE_SMALL, "day", "Enter days");
        boxLarge = new MovingElement("Large Box", PRICE_BOX_LARGE, "box", "Enter number of Boxes");
        boxSmall = new MovingElement("Small Box", PRICE_BOX_SMALL, "box", "Enter number of boxes");
    }

    /**
     *
     * @return return array of Object for JOptionPanne Input Dialog
     */
    public Object[] getInputOptions(){
        java.util.List<Object> inputOptions = new ArrayList<>();
        inputOptions.add("Choose from options below and enter the quantity\n"); // main title
        inputOptions.add("\nSelect Packages:-"); // title for input packages
        inputOptions.addAll(packageA.getInputOptinos());
        inputOptions.addAll(packageB.getInputOptinos());
        inputOptions.add("\nSelect Storage Options:-"); // title for Storage options
        inputOptions.addAll(storageSmall.getInputOptinos());
        inputOptions.addAll(storageLarge.getInputOptinos());
        inputOptions.add("\nSelect Boxes:-"); // title for Boxes
        inputOptions.addAll(boxSmall.getInputOptinos());
        inputOptions.addAll(boxLarge.getInputOptinos());

        return inputOptions.toArray();
    }

    /**
     *
     * @return return the receipt or error string if invalid input is provided
     */
    public String getOutputString(){
        MovingElement[] elements = {packageA, packageB, storageSmall, storageLarge, boxSmall, boxLarge};
        double service = 0, options = 0, items = 0;
        String outputString = "";
        for(MovingElement element : elements){

            if(element.checkBox.isSelected()){
                try{
                    element.setQuantity((int)Double.parseDouble(element.getInput().trim()));
                    double elementTotal = element.getPrice()*element.getQuantity();
                    outputString += "Total price for "+element.getName()+ " = $"+elementTotal+"\n\n";
                    if(element == packageA || element == packageB)
                        service += elementTotal;
                    if(element == storageSmall || element == storageLarge)
                        options += elementTotal;
                    if(element == boxSmall || element == boxLarge){
                        items += elementTotal;
                    }
                }catch (NumberFormatException e){
                    return "Try again! Please Enter all the values Correctly";
                }
            }
        }

        outputString += "Service Total = $"+service+"\n\n";
        outputString += "Options Total = $"+options+"\n\n";
        outputString += "Items Total = $"+items+"\n\n";
        outputString += "Total = $"+(service+options+items)+"\n\n";

        return outputString;
    }

    // inner class
    private static class MovingElement implements ActionListener{
        private final String name;
        private final JTextField textField;
        private final JCheckBox checkBox;
        private final JLabel label, lablePlaceholder, textPlaceholder;
        private int quantity;
        private final float price;

        MovingElement(String name, float price, String per, String promptString){
            this.name = name;
            this.textField = new JTextField();
            textField.setEnabled(false);
            textField.setVisible(false);
            this.label = new JLabel(promptString);
            this.label.setVisible(false);
            this.lablePlaceholder = new JLabel(".");
            this.textPlaceholder = new JLabel(".");
            this.price = price;
            this.checkBox = new JCheckBox(this.name+" | $"+this.price+"/"+per);
            this.checkBox.addActionListener(this);
            this.quantity = 0;
        }

        private void setQuantity(int quantity){
            this.quantity = quantity;
        }
        private float getTotal(){
            return this.price * this.quantity;
        }

        /**
         *
         * @return returns JOption pane input fields including title Lable, check box, TextField for each element
         */
        private java.util.List<Object> getInputOptinos(){
            java.util.List<Object> options = new ArrayList<>();
            options.add(this.checkBox);
            options.add(this.lablePlaceholder);
            options.add(this.textPlaceholder);
            options.add(this.label);
            options.add(this.textField);
            return options;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==this.checkBox){
                this.textField.setVisible(this.checkBox.isSelected());
                this.textField.setEnabled(this.textField.isVisible());
                this.label.setVisible(this.checkBox.isSelected());
                this.textPlaceholder.setVisible(!this.checkBox.isSelected());
                this.lablePlaceholder.setVisible(!this.checkBox.isSelected());
            }
        }

        public String getInput() {
            return textField.getText();
        }

        public int getQuantity() {
            return quantity;
        }

        public String getName() {
            return name;
        }

        public float getPrice() {
            return price;
        }
    }
}
