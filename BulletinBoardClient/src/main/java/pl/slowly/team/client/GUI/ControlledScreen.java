package pl.slowly.team.client.GUI;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import pl.slowly.team.client.connection.ClientController;

/**
 * @author Maxym
 */
public interface ControlledScreen {

    public void setScreenController(ScreensController screenPage, ClientController clientController);

    public void load();

}
