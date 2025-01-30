# EssentialsXAddon64
A spigot plugin that adds customizable placeholders and improves EssentialsX nicknames.

Created by tbm00 for play.mc64.wtf.

## Features
- Easily check who has which EssentialsX nickname
- Define replacement output for other plugins' placeholders

## Dependencies
- **Java 17+**: REQUIRED
- **Spigot 1.18.1+**: UNTESTED ON OLDER VERSIONS
- **PlaceholderAPI**: REQUIRED
- **EssentialsX**: OPTIONAL (not necessary if only using newPlaceholder section)


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
- \+ Unlimited replacement outputs for other plugins' placeholders

## Config
```
```