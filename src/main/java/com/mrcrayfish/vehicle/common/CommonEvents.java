package com.mrcrayfish.vehicle.common;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.obfuscate.common.event.EntityLivingInitEvent;
import com.mrcrayfish.vehicle.Reference;
import com.mrcrayfish.vehicle.entity.EntityVehicle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class CommonEvents
{
    public static final DataParameter<Boolean> PUSHING_CART = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.BOOLEAN);
    public static final DataParameter<NBTTagCompound> HELD_VEHICLE = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.COMPOUND_TAG);

    private static final List<String> IGNORE_ITEMS;
    private static final List<String> IGNORE_SOUNDS;
    private static final List<String> IGNORE_ENTITIES;

    static
    {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("body");
        IGNORE_ITEMS = builder.build();

        builder = ImmutableList.builder();
        builder.add("idle");
        builder.add("driving");
        IGNORE_SOUNDS = builder.build();

        builder = ImmutableList.builder();
        builder.add("vehicle_atv");
        builder.add("couch");
        IGNORE_ENTITIES = builder.build();
    }

    @SubscribeEvent
    public void onMissingItem(RegistryEvent.MissingMappings<Item> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<Item> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_ITEMS.contains(missing.key.getResourcePath()))
            {
                missing.ignore();
            }
        }
    }

    @SubscribeEvent
    public void onMissingSound(RegistryEvent.MissingMappings<SoundEvent> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<SoundEvent> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_SOUNDS.contains(missing.key.getResourcePath()))
            {
                missing.ignore();
            }
        }
    }

    @SubscribeEvent
    public void onMissingEntity(RegistryEvent.MissingMappings<EntityEntry> event)
    {
        for(RegistryEvent.MissingMappings.Mapping<EntityEntry> missing : event.getMappings())
        {
            if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_ENTITIES.contains(missing.key.getResourcePath()))
            {
                missing.ignore();
            }
        }
    }

    @SubscribeEvent
    public void onEntityInit(EntityLivingInitEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer)
        {
            event.getEntityLiving().getDataManager().register(PUSHING_CART, false);
            event.getEntityLiving().getDataManager().register(HELD_VEHICLE, new NBTTagCompound());
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();
        if(player.isSneaking())
        {
            if(!player.getDataManager().get(HELD_VEHICLE).hasNoTags())
            {
                if(event instanceof PlayerInteractEvent.RightClickBlock)
                {
                    if(!world.isRemote && event.getFace() == EnumFacing.UP)
                    {
                        BlockPos pos = event.getPos().up();
                        NBTTagCompound tagCompound = player.getDataManager().get(HELD_VEHICLE);
                        Entity entity = EntityList.createEntityFromNBT(tagCompound, world);
                        if(entity != null)
                        {
                            entity.setPositionAndRotation(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (player.getRotationYawHead() + 90F) % 360.0F, 0F);

                            world.spawnEntity(entity);
                            player.getDataManager().set(HELD_VEHICLE, new NBTTagCompound());
                        }
                    }
                }
                else if(event.isCancelable())
                {
                    event.setCanceled(true);
                }
            }
            else if(event instanceof PlayerInteractEvent.EntityInteract)
            {
                if(!world.isRemote)
                {
                    Entity targetEntity = ((PlayerInteractEvent.EntityInteract) event).getTarget();
                    if(targetEntity instanceof EntityVehicle)
                    {
                        NBTTagCompound tagCompound = new NBTTagCompound();
                        String id = getEntityString(targetEntity);
                        if(id != null)
                        {
                            tagCompound.setString("id", id);
                            targetEntity.writeToNBT(tagCompound);
                            player.getDataManager().set(HELD_VEHICLE, tagCompound);
                            world.removeEntity(targetEntity);
                        }
                    }
                }
            }
        }
        else if(!player.getDataManager().get(HELD_VEHICLE).hasNoTags())
        {
            if(event.isCancelable())
            {
                event.setCanceled(true);
            }
        }
    }

    @Nullable
    private String getEntityString(Entity entity)
    {
        ResourceLocation resourcelocation = EntityList.getKey(entity);
        return resourcelocation == null ? null : resourcelocation.toString();
    }
}
