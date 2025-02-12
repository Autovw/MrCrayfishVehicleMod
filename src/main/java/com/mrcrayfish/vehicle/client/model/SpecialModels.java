package com.mrcrayfish.vehicle.client.model;

import com.mrcrayfish.vehicle.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum SpecialModels implements ISpecialModel
{
    /* Sports Car */
    SPORTS_CAR_BODY("sports_car/body"),
    SPORTS_CAR_STEERING_WHEEL("sports_car/steering_wheel"),
    SPORTS_CAR_COSMETIC_STOCK_DASHBOARD("sports_car/cosmetics/stock_dashboard"),
    SPORTS_CAR_COSMETIC_STOCK_FRONT_BUMPER("sports_car/cosmetics/stock_front_bumper"),
    SPORTS_CAR_COSMETIC_STOCK_REAR_BUMPER("sports_car/cosmetics/stock_rear_bumper"),
    SPORTS_CAR_COSMETIC_STOCK_HOOD("sports_car/cosmetics/stock_hood"),
    SPORTS_CAR_COSMETIC_STOCK_LEFT_DOOR("sports_car/cosmetics/stock_left_door"),
    SPORTS_CAR_COSMETIC_STOCK_RIGHT_DOOR("sports_car/cosmetics/stock_right_door"),
    SPORTS_CAR_COSMETIC_STOCK_FRONT_LIGHTS("sports_car/cosmetics/stock_front_lights"),
    SPORTS_CAR_COSMETIC_STOCK_REAR_LIGHTS("sports_car/cosmetics/stock_rear_lights"),
    SPORTS_CAR_COSMETIC_STOCK_SEAT("sports_car/cosmetics/stock_seat"),
    SPORTS_CAR_COSMETIC_STOCK_SPOILER("sports_car/cosmetics/stock_spoiler"),

    /* Mini Bus */
    MINI_BUS_BODY("mini_bus/body"),
    MINI_BUS_STEERING_WHEEL("mini_bus/steering_wheel"),
    MINI_BUS_COSMETIC_STOCK_DASHBOARD("mini_bus/cosmetics/stock_dashboard"),
    MINI_BUS_COSMETIC_STOCK_LEFT_DOOR("mini_bus/cosmetics/stock_left_door"),
    MINI_BUS_COSMETIC_STOCK_RIGHT_DOOR("mini_bus/cosmetics/stock_right_door"),
    MINI_BUS_COSMETIC_STOCK_SLIDING_DOOR("mini_bus/cosmetics/stock_sliding_door"),
    MINI_BUS_COSMETIC_STOCK_SEAT("mini_bus/cosmetics/stock_seat"),
    MINI_BUS_COSMETIC_STOCK_ROOF("mini_bus/cosmetics/stock_roof"),
    MINI_BUS_COSMETIC_ROOF_RACKS("mini_bus/cosmetics/roof_racks"),
    MINI_BUS_COSMETIC_AIRCON_LADDER_REAR_DECOR("mini_bus/cosmetics/aircon_ladder"),
    MINI_BUS_COSMETIC_FRONT_ROOF("mini_bus/cosmetics/front_roof"),

    /* Moped */
    MOPED_BODY("moped/body"),
    MOPED_MUD_GUARD("moped/mud_guard"),
    MOPED_HANDLES("moped/handles"),
    MOPED_COSMETIC_STOCK_SEAT("moped/cosmetics/stock_seat"),
    MOPED_COSMETIC_STOCK_TRAY("moped/cosmetics/stock_tray"),
    MOPED_COSMETIC_STOCK_FRONT_LIGHT("moped/cosmetics/stock_front_light"),

    /* Dirt Bike */
    DIRT_BIKE_BODY("dirt_bike/body"),
    DIRT_BIKE_HANDLES("dirt_bike/handles"),

    /* Quad Bike */
    QUAD_BIKE_BODY("quad_bike/body"),
    QUAD_BIKE_HANDLES("quad_bike/handles"),

    /* Go Kart */
    GO_KART_BODY("go_kart/body"),
    GO_KART_STEERING_WHEEL("go_kart_steering_wheel"),

    JET_SKI_BODY("jet_ski_body"),
    LAWN_MOWER_BODY("lawn_mower_body"),
    SPORTS_PLANE("sports_plane_body"),
    SPORTS_PLANE_WING("sports_plane_wing"),
    SPORTS_PLANE_WHEEL_COVER("sports_plane_wheel_cover"),
    SPORTS_PLANE_LEG("sports_plane_leg"),
    SPORTS_PLANE_PROPELLER("sports_plane_propeller"),
    GOLF_CART_BODY("golf_cart_body"),
    OFF_ROADER_BODY("off_roader_body"),
    TRACTOR("tractor_body"),
    VEHICLE_TRAILER("trailer_body"),
    STORAGE_TRAILER("trailer_chest_body"),
    SEEDER_TRAILER("trailer_seeder_body"),
    FERTILIZER_TRAILER("trailer_fertilizer_body"),
    FLUID_TRAILER("trailer_fluid_body"),

    VEHICLE_CRATE_SIDE("vehicle_crate_panel_side"),
    VEHICLE_CRATE_TOP("vehicle_crate_panel_top"),
    JACK_PISTON_HEAD("jack_piston_head"),
    SEED_SPIKER("seed_spiker"),
    NOZZLE("nozzle"),
    TOW_BAR("tow_bar"),
    BIG_TOW_BAR("big_tow_bar"),
    FUEL_DOOR_CLOSED("fuel_door_closed"),
    FUEL_DOOR_OPEN("fuel_door_open"),
    SMALL_FUEL_DOOR_CLOSED("small_fuel_door_closed"),
    SMALL_FUEL_DOOR_OPEN("small_fuel_door_open"),
    KEY_HOLE("key_hole"),
    SOFA_HELICOPTER_ARM("sofa_helicopter_arm"),
    SOFA_HELICOPTER_SKID("sofa_helicopter_skid"),

    /* Mod dependent models */
    RED_SOFA(new ModelResourceLocation("cfm:red_sofa", "inventory"), false);

    // Add spray can lid
    /**
     * The location of an item model in the [MOD_ID]/models/vehicle/[NAME] folder
     */
    private ResourceLocation modelLocation;

    /**
     * Determines if the model should be loaded as a special model
     */
    private boolean specialModel;

    /**
     * Cached model
     */
    @Nullable
    @OnlyIn(Dist.CLIENT)
    private IBakedModel cachedModel;

    /**
     * Sets the model's location
     *
     * @param modelName name of the model file
     */
    SpecialModels(String modelName)
    {
        this(new ResourceLocation(Reference.MOD_ID, "vehicle/" + modelName), true);
    }

    /**
     * Sets the model's location
     *
     * @param resource name of the model file
     */
    SpecialModels(ResourceLocation resource, boolean specialModel)
    {
        this.modelLocation = resource;
        this.specialModel = specialModel;
    }

    public ResourceLocation getModelLocation()
    {
        return this.modelLocation;
    }

    /**
     * Gets the model
     *
     * @return isolated model
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public IBakedModel getModel()
    {
        if(this.cachedModel == null)
        {
            IBakedModel model = Minecraft.getInstance().getModelManager().getModel(this.modelLocation);
            if(model == Minecraft.getInstance().getModelManager().getMissingModel())
            {
                return model;
            }
            this.cachedModel = model;
        }
        return this.cachedModel;
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void register(ModelRegistryEvent event)
    {
        for(SpecialModels model : values())
        {
            if(model.specialModel)
            {
                ModelLoader.addSpecialModel(model.modelLocation);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void clearModelCache()
    {
        for(SpecialModels model : values())
        {
            model.cachedModel = null;
        }
    }
}
