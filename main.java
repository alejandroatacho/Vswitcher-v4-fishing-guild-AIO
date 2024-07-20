//Title: V4 AIO Fishing Guild
////____________________________________________________________________________________________
//Choose which Fish you want to fish
//____________________________________________________________________________________________
//User Decision variables 0 = Lobster, 1 = Tuna/Swordfish, 2 = Shark Fishing
int fishType = 2; //0 = Lobster, 1 = Tuna/Swordfish, 2 = Shark Fishing
int dragon_harpoon_special_attack = 0; //0 = No Harpoon, 1 = Equipped Dragon Harpoon
//__________________________________________________________________________________________

//Hinamizawa's Code Down Below;
int lvl = client.getBoostedSkillLevel(Skill.FISHING);
int barrel_fish_id = 25584;  // ID of the fish barrel
int closed_barrel_fish_id = 25582; // its closed
int closed_barrel_fish_slot_id = v.getInventory().slot(25582);
//WorldPoints
WorldPoint wp_deposit_box = new WorldPoint(2589, 3416, 0);// Deposit Box
WorldPoint wp1 = new WorldPoint(2595, 3415, 0); //human move
WorldPoint wp2 = new WorldPoint(2593, 3416, 0); //human move
WorldPoint wp3 = new WorldPoint(2594, 3414, 0); //human move
WorldPoint currentLocation = client.getLocalPlayer().getWorldLocation();
WorldPoint[] points = {wp1, wp2, wp3};
WorldPoint selectedPoint = points[new Random().nextInt(points.length)];
//Fishing Spots
NPC fishingSpot_Lobster = v.getNpc().findNearest(1510); // Lobster/Tuna/SwordFish
int [] shark_OPCode = {23279,23280};
int [] swordfish_tuna_OPCODE = {23302,23301};



//Methods down below
private void useSpecialAttack() {
    if (v.getCombat().specRemaining(100, 100)) {
        v.getCombat().spec(1);
    }
}

private void enableRunning() {
    if (client.getEnergy() == 2000) {
        v.getWalking().turnRunningOn();
    }
}

private void baitSpot_1() {
    if (!v.getInventory().inventoryFull() && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving()) {
        v.getFishing().cage(fishingSpot_Lobster);
    }
}
private void baitSpot_2() {
    if (!v.getInventory().inventoryFull() && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving()) {
        Random rand = new Random();
        int opcode = swordfish_tuna_OPCODE[rand.nextInt(swordfish_tuna_OPCODE.length)];
        v.invoke("Harpoon","<col=ffff00>Fishing spot", opcode, 11, 0, 0, false);
    }
}

private void baitSpot_3() {
    if (!v.getInventory().inventoryFull() && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving()) {
				Random rand = new Random();
				int opcode = shark_OPCode[rand.nextInt(shark_OPCode.length)];
				v.invoke("Harpoon","<col=ffff00>Fishing spot", opcode, 11, 0, 0, false);
	
    }
}

private void openFishBarrel() {
    if (v.getInventory().hasIn(closed_barrel_fish_id)) {
        v.invoke("Open","<col=ff9040>Fish barrel</col>",4,57,closed_barrel_fish_slot_id,9764864,false);
    }
}



private void FishNow() {   
        if (fishType == 0 && !v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
            baitSpot_1();
        }
        else if (fishType == 1 && !v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
            baitSpot_2();
        }
        else if (fishType == 2 && !v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
            baitSpot_3();
        }
}

private void bankManagement(){
    GameObject bankChest = v.getGameObject().findNearest(10529);
    if (bankChest != null) {
        int bankChestSceneX = bankChest.getSceneMinLocation().getX();
        int bankChestSceneY = bankChest.getSceneMinLocation().getY();

        v.invoke("Deposit","<col=ffff>Bank Deposit Box",10529,3,bankChestSceneX,bankChestSceneY,false);
        v.getCallbacks().afterTicks(3, () -> {
            v.invoke("Default Quantity: <col=ff9040>All</col>","",1,57,-1,12582931,false);
            if (v.getInventory().hasIn(barrel_fish_id)) {
                v.invoke("Empty","<col=ff9040>Open fish barrel</col>",9,1007,0,12582914,false);
            }                 
            if (fishType == 0) {
                v.invoke("Deposit-All","<col=ff9040>Raw lobster</col>",1,57,4,12582914,false);
            } else if (fishType == 1) {
                for (int slot = 2; slot < 6; slot++) {
                    v.invoke("Deposit-All","<col=ff9040>Swordfish</col>",1,57,slot,12582914,false);
                    v.invoke("Deposit-All","<col=ff9040>Tuna</col>",1,57,slot,12582914,false);
                }
            } else if (fishType == 2) {
                v.invoke("Deposit-All","<col=ff9040>Raw shark</col>",1,57,4,12582914,false);
            }
            v.invoke("Close","",1,57,11,12582913,false);
        });
    }
}

private void moveAndDepositItems() {
   if (v.getInventory().inventoryFull() && v.getLocalPlayer().hasAnimation(-1) && !v.getWalking().isMoving()) {
        WorldPoint currentLocation = client.getLocalPlayer().getWorldLocation();
        if ((!currentLocation.equals(wp1) || !currentLocation.equals(wp2) || !currentLocation.equals(wp3)) && !currentLocation.equals(wp_deposit_box) && v.getInventory().inventoryFull()) { //d
            WorldPoint selectedPoint = points[new Random().nextInt(points.length)];
            v.getWalking().walk(selectedPoint);
            if ((currentLocation.equals(wp1) || currentLocation.equals(wp2) || currentLocation.equals(wp3)) && !v.getWalking().isMoving())
             v.getCallbacks().afterTicks(3, () -> {

                    v.getWalking().walk(wp_deposit_box);
                    });


        }
    } else if ((currentLocation.equals(wp1) || currentLocation.equals(wp2) || currentLocation.equals(wp3)) && !v.getWalking().isMoving() && v.getInventory().inventoryFull()) {
        v.getWalking().walk(wp_deposit_box);
    } 
     else if (!v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1) && currentLocation.equals(wp_deposit_box) && v.getInventory().inventoryFull()) {
     bankManagement();
     }
}


private void walkToFish() {
    if (!v.getInventory().inventoryFull() && currentLocation.equals(wp_deposit_box) && v.getLocalPlayer().hasAnimation(-1)) {
        WorldPoint selectedPoint = points[new Random().nextInt(points.length)];
        v.getWalking().walk(selectedPoint);
    }
    else if ((currentLocation.equals(wp1) || currentLocation.equals(wp2) || currentLocation.equals(wp3)) && !v.getInventory().inventoryFull())
    {
                
       FishNow();
    }
    else if (currentLocation.equals(wp_deposit_box) && v.getInventory().inventoryFull()) {
     bankManagement();
     }
}

private void runScript() {
    openFishBarrel();
    enableRunning();
    if (dragon_harpoon_special_attack == 1){
        useSpecialAttack();
    }
    FishNow();
    moveAndDepositItems();
    walkToFish();
}
runScript();