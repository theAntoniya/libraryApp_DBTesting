package com.cydeo.steps;

import com.cydeo.pages.UsersPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.*;
import org.junit.Assert;

import java.nio.file.attribute.UserPrincipal;


public class usersStepDef {

    UsersPage usersPage=new UsersPage();
    String email;
    String expectedStatus;

    @When("the user clicks Edit User button")
    public void the_user_clicks_edit_user_button() {
        BrowserUtil.waitFor(2);
        usersPage.editUser.click();
    }
    @When("the user changes user status {string} to {string}")
    public void the_user_changes_user_status_to(String active, String inactive) {
        BrowserUtil.waitFor(2);

       /* Select select=new Select(usersPage.statusDropdown);
        select.selectByVisibleText(inactive);*/

        BrowserUtil.selectByVisibleText(usersPage.statusDropdown,inactive);

        email = usersPage.email.getAttribute("value");
        System.out.println("email = " + email);

        BrowserUtil.waitFor(2);

        expectedStatus=inactive;
    }
    @When("the user clicks save changes button")
    public void the_user_clicks_save_changes_button() {

        usersPage.saveChanges.click();
        System.out.println("----> Users "+email+" is deactivated");

    }
    @Then("{string} message should appear")
    public void message_should_appear(String expectedMessage) {
        // Maybe this message will appear dynamicly.In that case  you need to handle time issue with Explicit Wait
        BrowserUtil.waitFor(1);
        String actualMessage = usersPage.toastMessage.getText();
        Assert.assertEquals(expectedMessage,actualMessage);

    }
    @Then("the users should see same status for related user in database")
    public void the_users_should_see_same_status_for_related_user_in_database() {

        DB_Util.runQuery("select status from users where email='"+email+"'");

        //Get data
        String actualStatus = DB_Util.getFirstRowFirstColumn();

        //verify
        Assert.assertEquals(expectedStatus,actualStatus);


    }
    @Then("the user changes current user status {string} to {string}")
    public void the_user_changes_current_user_status_to(String inactive, String active) {
        // This step for switch users page to INACTIVE users
        BrowserUtil.waitFor(1);
        BrowserUtil.selectByVisibleText(usersPage.userStatusDropdown,inactive);

        //to find current we need expand page.Thats why we created one more step to see all user in one page
        // if you have more than 500 create loop to find your users (next page )
        BrowserUtil.waitFor(1);
        BrowserUtil.selectByVisibleText(usersPage.NumberOfUserDropdown,"500");


        // We are gonna click editUser button for current user in INACTIVE PAGE
        BrowserUtil.waitFor(1);
        usersPage.editUser(email).click();

        // We updated current user status INACTIVE to ACTIVE
        BrowserUtil.waitFor(1);
        BrowserUtil.selectByVisibleText(usersPage.statusDropdown,active);

        //Click save changes
        BrowserUtil.waitFor(1);
        usersPage.saveChanges.click();

        System.out.println("----> Users "+email+" is activated");


    }
}
