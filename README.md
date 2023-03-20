# BuddyTP
## Goals / Philosophy
In a semi-vanilla survival multiplayer setting the use of teleportation between players can make creating transport infrastructure (nether highways, piston bolts, etc) in game much less important. This effectively removes a large part of vanilla gameplay. However not having any teleportation creates a higher barrier to entry for players who are joining a preexisting group on the server (for example an irl friend group). This is because players tend to build farther away from spawn on public servers so they are not in the way of other players.

BuddyTP aims to solve this issue by giving every player **one** free teleport.

## Features
- One free teleport per player
- Fully customizable messages
- Full permission support
- Ability to limit what worlds you can teleport in
- (Optional) Ability to purchase another teleport for an item
- (Optional) Automatically set a players home if they do not have one already.
  - This solves if a player were to die after using their free teleport before they set it themselves
  - Currently this only supports EssentialsX homes, if you use a different homes plugin feel free to request support on the [GitHub](https://github.com/bonn2/buddytp) or [Discord](https://discord.gg/Swkqv3Tp4R)

## Commands / Permissions
|Command|Alias|Usage|Permission|Default|Description|
|---|---|---|---|---|---|
|`/buddytp`|`/btp`|`/buddytp <player>`|`buddytp.send`|`everyone`|Teleport to a friend, once|
|`/buddytpaccept`|`/btpaccept`|`/buddytpaccept [player]`|`buddytp.accept`|`everyone`|Accept a teleport request|
|`/buddytpdeny`|`/btpdeny`|`/buddytpdeny [player]`|`buddytp.deny`|`everyone`|Deny a teleport request|
|`/buddytpcancel`|`/btpcancel`|`/buddytpcancel`|`buddytp.cancel`|`everyone`|Cancel a teleport request|
|`/buybuddytp`||`/buybuddytp`|`buddytp.buy`|`everyone`|Buy a new buddytp|
|`/resetbuddytp`||`/resetbuddytp <player>`|`buddytp.reset`|`op`|Reset a players free buddytps|
