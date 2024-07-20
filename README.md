## AIO Fishing Guild Fish Script

This script allows you to fish for different types of fish in the Fishing Guild, including lobsters, raw tuna/swordfish, and sharks. You can select the type of fish to catch by adjusting the `fishType` variable.

the way I bank swordfish, tuna is a bit different but its magical it works even if it fails 1 trip it will catch on next trip but base on probabilaty calculation it should always succeed in depositing both tuna and swordfish.
the rest gets bank normally, if you feel like the script is choosing to many fishing spots, delete some of the integers in the opcode array.

### Setup
1. Copy the provided Java code into your code editor.
2. Save the file with an appropriate name.
3. Tick Execution 3 (trigger)
4. Wear Dragon harpoon & Lightbearer Ring
5. put lobster cage (on 2nd slot)
6. put Fish Barrel (1st slot)

### Usage
1. Start the script inside the Fishing Guild.
2. Ensure you have the necessary items and equipment equipped for fishing.
3. The script will perform the following actions based on the selected `fishType`:

   - If `fishType` is 0 (lobsters):
     - Cage the fishing spot for lobsters.
   - If `fishType` is 1 (tuna/swordfish):
     - Harpoon the fishing spot for tuna or swordfish.
   - If `fishType` is 2 (sharks):
     - Harpoon the fishing spot for sharks.
   - Open the fish barrel if it is in the inventory and it is closed.
   - Move to the deposit box and deposit items in the bank when the inventory is full and the player is not moving.
   - Walk back to the fishing spot if the inventory is not full and the player is at the deposit box.

## The First Line Of Of The Code!
int fishType = 0; // pick the number  0 = Lobster, 1 = Tuna/Swordfish, 2 = Shark Fishing


**Note**: The information provided above is for reference only. Please ensure that you understand the code and its implications before implementing it in Vswitcher.