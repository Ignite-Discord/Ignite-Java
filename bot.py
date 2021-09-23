# Libs
from typing import Counter
import discord
import json  # For interacting with json
from pathlib import Path  # For paths
import platform  # For stats
import logging
from dataclasses import dataclass
import randomstuff
import json
from discord.ext import commands  # For discord
import requests
from prsaw import RandomStuff
from discord.ext.commands import has_permissions, CheckFailure

# initiate the object with async mode
client=randomstuff.Client(api_key="8gFgqMTpz0nD")
async_client=randomstuff.AsyncClient(api_key="8gFgqMTpz0nD")
REDDIT_APP_ID = "8nDq-qsV2EqjE6uuPFoOYQ"
REDDIT_APP_SECRET="yy0NstxdKynhUFTVKYV_8dWHvwyy9w"
# Get current working directory
cwd = Path(__file__).parents[0]
cwd = str(cwd)
print(f"{cwd}\n-----")

# Defining a few things
DEFAULTPREFIX = '.'
#secret_file = json.load(open(cwd + '/bot_config/secrets.json'))
bot = commands.Bot(command_prefix='ign ', case_insensitive=True)  # , owner_id=756308319622397972)
#bot.config_token = secret_file['token']
logging.basicConfig(level=logging.INFO)
author = None
bot.version = '0.0.3'
bot.remove_command("help")

@bot.event
async def on_ready():
    print(f"-----\nLogged in as: {bot.user.name} : {bot.user.id}\n-----\nMy current prefix is: ign\n-----")
    

@bot.event
async def on_command_error(ctx, error):
    # Ignore these errors
    ignored = (commands.CommandNotFound, commands.UserInputError)
    if isinstance(error, ignored):
        return

    # Begin error handling
    if isinstance(error, commands.CommandOnCooldown):
        m, s = divmod(error.retry_after, 60)
        h, m = divmod(m, 60)
        if int(h) is 0 and int(m) is 0:
            await ctx.send(f' You must wait {int(s)} seconds to use this command!')
        elif int(h) is 0 and int(m) is not 0:
            await ctx.send(f' You must wait {int(m)} minutes and {int(s)} seconds to use this command!')
        else:
            await ctx.send(f' You must wait {int(h)} hours, {int(m)} minutes and {int(s)} seconds to use this command!')
    elif isinstance(error, commands.CheckFailure):
        await ctx.send("You lack permission to use this command.")
    raise error



@bot.command()
async def talk(ctx, *, message=None):
    response = client.get_ai_response(message)
    await ctx.reply(response.message)
    print (response.message)

@bot.event
async def on_message(message):  
    a=message.content
    msg=a.lower()
    if bot.user== message.author:
        return
    if message.channel.id == 883894223668121680:
        if "what is ignite" in msg:
            await message.reply("Ignite is Grace Christian Church of the Philippines' High School Ministry. Our goal is for every high school student to have ignited hearts for Christ.")
        if "ignite" == msg:
            await message.reply("Ignite is Grace Christian Church of the Philippines' High School Ministry. Our goal is for every high school student to have ignited hearts for Christ.")
        elif "ignite is what" in msg:
            await message.reply("Ignite is Grace Christian Church of the Philippines' High School Ministry. Our goal is for every high school student to have ignited hearts for Christ.")
        elif "coil" in msg:
            await message.reply("COIL is Ignite Ministry's Official Discord Server. COIL was named after a car's ignition coil that ignites power in a car's engine. In COIL, we desire to provide a safe space for all high school students so that they will be encouraged to continue igniting their spiritual lives for God's glory.")
        elif "ministry" in msg:
            await message.reply("Currently, we have two online ministries: Ignite the Night, happens every Friday at 8:00 PM, is a fun-filled gathering of high school students with interactive activities and messages that caters to the needs of their generation. Ignite Sundays, happens every Sunday at 9:00 AM, is a Sunday school for high school students (Grade 7-10 only) that focuses on in-depth Bible studies and fun activities. Ignite Ministries also hosts events such as Ignite Game Night (IGN) wherein Igniters play classic board and table-top games, game show style! We also host CLUEDO nights and Valentines celebrations! ")
        elif "ignite coin" in msg:
            await message.reply("Ignite Coins is Ignite's official digital currency used to purchase in Ignite's online pop-up store every December and July. Igniters can earn Ignite Coins by attending various Ignite gatherings. Igniters can earn 10 coins everytime they come on time for our weekly gatherings, 3 if they're late, 5 if cameras are open and is seen on screen (yep, not just eyes and forehead!), and 8 for every new Igniter invited! ")
        elif "allowed to join ignite" in msg:
            await message.reply("Definitely, yes! As long as you're a high school student, you are more that welcome to join us!")
        elif "i need someone to talk to" in msg:
            await message.reply("Your Ignite Ahyas and Achis are always here for you! We want to journey life with you, whether it be in the highs or even through the lows. Simply send us a message here in Discord or FB Messenger! We will try our best to be of help to you.")
        elif "grace christian church of the philippines" in msg:
            await message.reply("GCCP is our home! To know more about our church, click on this link: ( https://gccp.org.ph/new-here-2/ )")
        elif "gccp" in msg:
            await message.reply("GCCP is our home! To know more about our church, click on this link: ( https://gccp.org.ph/new-here-2/ )")
        elif "is ignite's fb page and fb group different" in msg:
            await message.reply("Yes! Ignite's FB page is public, while our group is only for Igniters (high school studnets) or their parent's FB account in case they don't have one. This is to ensure the safety of our Igniters! To follow our FB page, click on this link: ( https://www.facebook.com/GCCPHighSchool/ ) To join our FB group, click on this link: ( https://www.facebook.com/groups/322843642426914/?ref=share )")
        elif "what if i am not a chistian can i attend ignite" in msg:
            await message.reply("By all means, yes! You are more than welcome! Our hope is that you'll be able to know Jesus, our Lord and Savior, who paid the penalty of our sins on the cross, to give us eternal life. Simply because He loves us so much!")
        elif "ignite believe in" in msg:
            await message.reply("Ignite, same as GCCP, believes in the same thing! Read about our church's Statement of Faith in this link: ( https://gccp.org.ph/our-statement-of-faith/ )")
        elif "ignite believes in" in msg:
            await message.reply("Ignite, same as GCCP, believes in the same thing! Read about our church's Statement of Faith in this link: ( https://gccp.org.ph/our-statement-of-faith/ )")
        elif "I AM A SENIOR HIGH SCHOOL STUDENT. HOW DO I WORSHIP ON SUNDAYS" in msg:
            await message.reply("We would love for you to mature in your spiritual life as well! GCCP provides weekly worship guide for senior high school students and up! Here's the link to our weekly worship guide: ( https://gccp.org.ph/all-posts/category/sermons/ )")
        elif "COIL STUDY HALL" in msg:
            await message.reply("Think of it as our online coffee shop where you and your friends can gather and study together! Check out the Study Hall's rules channel for more information!")
        elif "COIL'S STUDY HALL" in msg:
            await message.reply("Think of it as our online coffee shop where you and your friends can gather and study together! Check out the Study Hall's rules channel for more information!")
        elif "AHYAS AND ACHIS" in msg:
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888576932407545876/0001-7791209199_20210914_014317_0000.png")
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888974696371077150/Cyber.png")
        elif "ahya" in msg:
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888576932407545876/0001-7791209199_20210914_014317_0000.png")
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888974696371077150/Cyber.png")
        elif "achi" in msg:
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888576932407545876/0001-7791209199_20210914_014317_0000.png")
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888974696371077150/Cyber.png")
        elif "ahyas" in msg:
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888576932407545876/0001-7791209199_20210914_014317_0000.png")
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888974696371077150/Cyber.png")    
        elif "achis" in msg:
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888576932407545876/0001-7791209199_20210914_014317_0000.png")
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888974696371077150/Cyber.png")
        elif "ahia" in msg:
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888576932407545876/0001-7791209199_20210914_014317_0000.png")
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888974696371077150/Cyber.png")
        elif "ahias" in msg:
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888576932407545876/0001-7791209199_20210914_014317_0000.png")
            await message.reply("https://cdn.discordapp.com/attachments/757130997178302506/888974696371077150/Cyber.png")
        elif "time" in msg and "ignite" in msg:
            await message.reply("This is still under development <a:loading:870870083285712896>")
        elif "night" in msg and "ignite" in msg:
            await message.reply("Ignite the Night happens every Friday :)")
        elif "ignite" in msg and "sunday" in msg:
            await message.reply("Ignite Sundays happens every Sunday :)")
        else:
            response=None
            response = await async_client.get_ai_response(message.content)
            messageai = response.message
            await message.reply(messageai)
    await bot.process_commands(message)
        

@bot.command()
async def ping(ctx):
    """
    Gets the ping of the bot.
    """
    await ctx.send(f'Server Ignite:\nPing: {round(bot.latency * 1000)}ms <a:loading:870870083285712896>')

@bot.command()
@commands.has_permissions(manage_channels=True)
async def lock(ctx, channel : discord.TextChannel=None):
    channel = channel or ctx.channel
    overwrite = channel.overwrites_for(ctx.guild.default_role)
    overwrite.send_messages = False
    await channel.set_permissions(ctx.guild.default_role, overwrite=overwrite)
    await ctx.send('Channel Locked. <a:blobdance:888591827484160020>')

@bot.command()
@commands.has_permissions(manage_channels=True)
async def unlock(ctx, channel : discord.TextChannel=None):
    channel = channel or ctx.channel
    await channel.set_permissions(ctx.guild.default_role, overwrite=False)
    await ctx.send('Channel Unlocked. <a:blobdance:888591827484160020>')

@bot.command()
@commands.has_permissions(manage_channels=True)
async def lockdown(ctx):
    for channel in ctx.guild.channels:
        await channel.set_permissions(ctx.guild.default_role, send_messages=False)
    await ctx.send('The server is now on lockdown! <a:blobdance:888591827484160020>')

#unlockdown
@bot.command()
@commands.has_permissions(manage_channels=True)
async def unlockdown(ctx):
    for channel in ctx.guild.channels:
        await channel.set_permissions(ctx.guild.default_role, overwrite=None)
    await ctx.send('Server is now unlocked! <a:blobdance:888591827484160020>')

bot.run("ODg4NTg2MjgyMDk4MzY0NDM3.YUU2dg.bMx0wcF6ttNAz6fF6RW8KLI0fnE")  # Runs our bot
