**🔍 Overview:**
DuelPlugin is a customizable dueling system for Minecraft Spigot 1.8.8 servers. It enables players to challenge each other to duels within designated arenas, enhancing competitive gameplay and player interaction.

**✨ Key Features:**

1. **🎮 Player Duels:**
   - **Challenge Players:** Use `/duel [player] [arena]` to send duel requests.
   - **Respond to Duels:** Accept or decline with `/duel accept [player]` or `/duel decline [player]`.

2. **🏟️ Arena Management:**
   - **Create Arenas:** Administrators create arenas using `/duel create [arena]`.
   - **Set Spawn Points:** Define spawn points separately with `/duel setspawn1 [arena]` and `/duel setspawn2 [arena]`.
   - **List Arenas:** View available arenas using `/duel arenalist`.

3. **💰 Rewards System:**
   - **Coin Rewards:** Winners earn configurable coins per duel, adjustable in `config.yml`.

4. **📊 Player Statistics:**
   - **View Stats:** Players can check their duel performance with `/duel stats`, displaying wins, losses, and coins.

5. **🔐 Permissions:**
   - **Granular Control:** 
     - `duel.use` for general duel commands.
     - `duel.create`, `duel.setspawn1`, `duel.setspawn2` for arena management.
     - `duel.admin` to inherit all administrative permissions.
   - **Integration:** Compatible with permission plugins like LuckPerms and PermissionsEx.

6. **🛠️ User Experience:**
   - **Tab Completion:** Auto-completes arena names when using `/duel [player] [arena]`.
   - **Clear Messaging:** Informative and color-coded messages guide players through commands and duel statuses.

**📥 Installation:**
1. Place `DuelPlugin.jar` in the server's `plugins` folder.
2. Start the server to generate configuration files.
3. Configure `config.yml` as needed.
4. Assign permissions using a permission management plugin.
5. Restart the server to activate DuelPlugin.

**📜 Commands Summary:**

| **Command**                  | **Description**                           | **Permission**       |
|------------------------------|-------------------------------------------|-----------------------|
| `/duel [player] [arena]`     | Send a duel request                       | `duel.use`            |
| `/duel accept [player]`      | Accept a duel request                     | `duel.use`            |
| `/duel decline [player]`     | Decline a duel request                    | `duel.use`            |
| `/duel create [arena]`       | Create a new duel arena                   | `duel.create`         |
| `/duel setspawn1 [arena]`    | Set spawn point 1 for the arena           | `duel.setspawn1`      |
| `/duel setspawn2 [arena]`    | Set spawn point 2 for the arena           | `duel.setspawn2`      |
| `/duel arenalist`            | List all available duel arenas            | `duel.use`            |
| `/duel stats`                | View your duel statistics                 | `duel.use`            |

**🖥️ Compatibility:**
- **Minecraft Version:** Spigot 1.8.8
- **Java Version:** Java 8
- **Dependencies:** Spigot API and Gson library.

---

**⚠️ Note:** Always back up your server before installing new plugins to prevent data loss.