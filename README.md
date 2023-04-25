# BuddyTP
## Goals / Philosophy
In a semi-vanilla survival multiplayer setting the use of teleportation between players can make creating transport infrastructure (nether highways, piston bolts, etc) much less important. This effectively removes a large part of vanilla gameplay. However not having any teleportation creates a higher barrier to entry for players who are joining a preexisting group on the server (for example an irl friend group). This is because players tend to build farther away from spawn on public servers so they are not in the way of other players.

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

## Messages
- `%sender%` The player who is making the request.
- `%target%` The player who is receiving the request.
- `%timeout%` The time in seconds that a request is valid for.

|Key|Placeholders|
|---|---|
|`only-players`||
|`no-pending-requests`||
|`multiple-pending-requests`||
|`no-active-request`|`%sender%`|
|`used-buddy-tp`||
|`could-not-find-player`|`%target%`|
|`teleport-to-self`||
|`already-has-buddy-tp`|`%target%`|
|`you-already-have-buddy-tp`||
|`price-too-high`||
|`bought-buddy-tp`||
|`reset-buddy-tp`|`%target%`|
|`your-buddy-tp-reset`||
|`request-target`|`%target%` `%sender%` `%timeout%`|
|`request-sender`|`%target%` `%sender%` `%timeout%`|
|`accepted-target`|`%target%` `%sender%`|
|`accepted-sender`|`%target%` `%sender%`|
|`denied-target`|`%target%` `%sender%`|
|`denied-sender`|`%target%` `%sender%`|
|`canceled-target`|`%target%` `%sender%`|
|`canceled-sender`|`%target%` `%sender%`|
|`timed-out-target`|`%target%` `%sender%`|
|`timed-out-sender`|`%target%` `%sender%`|
|`different-world`|`%target%`|
|`not-in-this-world`||
|`home-set`||

##  Community Translations
- (1.0.3) [Chinese](https://github.com/bonn2/BuddyTP/blob/master/translations/chinese.txt) by xiaoyueyoQvQ
### How to install a translation
1. Open the link to the desired translation
2. Copy the contents into your `~/plugins/BuddyTP/messages.txt` file
3. Restart the server
