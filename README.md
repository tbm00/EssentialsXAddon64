# EssentialsXAddon64
A spigot plugin that adds customizable placeholders and improves EssentialsX nicknames.

Created by tbm00 for play.mc64.wtf.

## Features
- Easily check who has an active EssentialsX nickname.
- Define replacement output for other plugins' placeholders.

## Dependencies
- **Java 17+**: REQUIRED
- **Spigot 1.18.1+**: UNTESTED ON OLDER VERSIONS
- **PlaceholderAPI**: REQUIRED
- **EssentialsX**: OPTIONAL (necessary if using nicknameExtension)


## Commands
#### Player Commands
- `/nicks` Display list of online nicknames
- `/nicks <player>` View online player's nickname

#### Admin Commands
- `/nicks reload` Reload nickname cache

## Permissions
#### Player Permissions
- `essentialsxaddon64.nicklist` Ability to display list of online nicknames *(default: everyone)*.
- `essentialsxaddon64.whois` Ability to view online player's nickname *(default: everyone)*.

#### Admin Permissions
- `essentialsxaddon64.reloadcache` Ability to reload nickname cache *(default: op)*.

## Placeholders
- `essentialsxaddon64_displayname` Returns player's EssentialsX nickname if they have one, else it returns their displayname.
- `essentialsxaddon64_username` Returns player's EssentialsX nickname if they have one, else it returns their username.
- \+ Unlimited replacement outputs for other plugins' placeholders.

## Config
```
# EssentialsXAddon64 v0.0.2-beta by @tbm00
# https://github.com/tbm00/EssentialsXAddon64

nicknameExtension:
  enabled: true

  # Reload nickname cache every X ticks
  autoCacheReloader:
    enabled: true
    ticksBetween: 6000
    
  # Appended to the start of the %essentialsxaddon64_displayname%
  # and %essentialsxaddon64_username% built-in placeholders
  nicknamePrefix: '~'

# Define your own replacement output for other plugins' placeholders
newPlaceholders: []
  #essentials_afk: # creates %essentialsxaddon64_essentials_afk%
    #'yes': ' &8*AFK*&r'
    #'no': ''
  #supervanish_isvanished: # creates %essentialsxaddon64_supervanish_isvanished%
    #'Yes': ' &e*VANISHED*&r'
    #'No': ''
  #deluxecombat_has_protection: # creates %essentialsxaddon64_deluxecombat_has_protection%
    #'true': ' &6*GRACE*&r'
    #'false': ''
    #'null': ''
```