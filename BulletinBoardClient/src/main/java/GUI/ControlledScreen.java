package GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import connection.Client;

/**
 * @author Maxym
 */
public interface ControlledScreen {

    public void setScreenController(ScreensController screenPage, Client client);

    public void load();

}
