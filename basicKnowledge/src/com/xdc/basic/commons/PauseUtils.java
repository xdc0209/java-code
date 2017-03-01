package com.xdc.basic.commons;

import java.util.Scanner;

public class PauseUtils
{
    private static Scanner scanner = new Scanner(System.in);

    public static void pressEnterToContinue()
    {
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }
}