# Rainforest Ecosystem & Kongra

A Fabric mod for Minecraft 1.20.1 that turns the rainforest into a survival challenge.

## What this mod adds

- **Acidic Rainforest Rain** — Stand out in the rain for too long (about 10 seconds) and you'll start losing health, one heart every couple of seconds. Get under cover, or wear the full KONGRA armor set to stay safe.
- **KONGRA** — The Big Boss. Body of a gorilla, head of a King Cobra. 300 HP, heavy armor, powerful knockback, and a venomous bite that poisons and weakens you. You do **not** stand a chance without KONGRA armor.
- **KONGRA Armor** — A powerful armor set crafted from the hardest-to-find rainforest materials (Rainforest Gems and Kongra Scales). Wearing the full set protects you from the deadly rain and gives you a fighting chance against KONGRA.
- **Jungle Toucan** — A skittish, breedable rainforest bird.
- **Rainforest Jaguar** — A fast, aggressive jungle predator that hunts toucans.

All items and spawn eggs are available in the **Rainforest & Kongra** creative tab.

---

## How to build the mod (NO Java install needed — GitHub builds it for FREE)

You do not need to install Java or any developer tools. GitHub will build the `.jar` file for you in the cloud, for free.

### Steps

1. **Create a GitHub account** at https://github.com (free).
2. Click the **+** in the top right → **New repository**. Give it any name and click **Create repository**.
3. On the new empty repository page, click the link that says **"uploading an existing file"**.
4. **Extract the ZIP** you downloaded for this mod so you have a normal folder of files.
5. **⚠️ macOS users — IMPORTANT:** This project contains a hidden folder named `.github` that is required for the build to run. Finder hides it by default. In Finder, press **Cmd + Shift + .** (period) to make hidden files visible. If you skip this step, the `.github` folder will not be uploaded and **the build will never run.**
6. Open the extracted folder and select **ALL files and folders INSIDE it** — including the hidden `.github` folder. Do **not** drag the outer folder itself; drag its **contents**.
7. Drag everything into the GitHub upload area.
8. Scroll down and click **Commit changes**.
9. Click the **Actions** tab at the top of your repository.
10. Wait about **2 minutes** for the build to finish (green checkmark).
11. Click the completed build run, then download the **mod-jar** artifact at the bottom.
12. Unzip it to get the `.jar` file, then copy that `.jar` into your `.minecraft/mods/` folder.

You must have **Fabric Loader** and the **Fabric API** installed for Minecraft 1.20.1.

---

## Notes

- Custom entities (KONGRA, Jungle Toucan, Rainforest Jaguar) are functional and fully spawn and behave correctly. They use a default appearance since no custom renderer is bundled — a custom look can be added later.
- Use the spawn eggs from the creative tab to summon them, or spawn KONGRA with `/summon rainforestkongra:kongra`.