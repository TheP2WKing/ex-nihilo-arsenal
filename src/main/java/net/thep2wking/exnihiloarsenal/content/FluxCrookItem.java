package net.thep2wking.exnihiloarsenal.content;

import static cofh.lib.util.constants.Constants.UUID_TOOL_REACH;
import static cofh.lib.util.helpers.StringHelper.getTextComponent;
import static cofh.redstonearsenal.init.RSAReferences.FLUX_GLOW_AIR;
import static net.minecraft.block.Blocks.AIR;
import static net.minecraft.util.text.TextFormatting.GRAY;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cofh.core.init.CoreConfig;
import cofh.core.util.ProxyUtils;
import cofh.lib.util.Utils;
import cofh.redstonearsenal.item.IMultiModeFluxItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolType;
import net.thep2wking.exnihiloarsenal.api.CrookItemLikeCoFH;
import net.thep2wking.exnihiloarsenal.config.ExNihiloArsenalConfig;
import novamachina.exnihilosequentia.api.ExNihiloRegistries;

public class FluxCrookItem extends CrookItemLikeCoFH implements IMultiModeFluxItem 
{
    protected final int LOW_LIGHT_THRESHOLD = 5;
    protected final int REMOVE_RADIUS = 10;
    protected final float damage;
    protected final float attackSpeed;

    protected final int maxEnergy;
    protected final int extract;
    protected final int receive;

    public FluxCrookItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, int energy, int xfer, Properties builder) 
    {
        super(tier, attackDamageIn, attackSpeedIn, new HashSet<>(), builder.addToolType(ToolType.get("crook"), 0).stacksTo(0));

        this.damage = getAttackDamage();
        this.attackSpeed = attackSpeedIn;

        this.maxEnergy = energy;
        this.extract = xfer;
        this.receive = xfer;

        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("charged"), this::getChargedModelProperty);
        ProxyUtils.registerItemModelProperty(this, new ResourceLocation("empowered"), this::getEmpoweredModelProperty);
    }
    
    //modified durability
    @Override
    public boolean isDamageable(ItemStack stack) 
    {
    	return false;
    }

    @Override
    @OnlyIn (Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) 
    {
        if (Screen.hasShiftDown() || CoreConfig.alwaysShowDetails)
        {
            tooltipDelegate(stack, worldIn, tooltip, flagIn);
        } 
        else if (CoreConfig.holdShiftForDetails) 
        {
            tooltip.add(getTextComponent("info.cofh.hold_shift_for_details").withStyle(GRAY));
        }
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) 
    {
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) 
    {
        return getItemEnchantability(stack) > 0;
    }

    public float getEfficiency(@Nonnull ItemStack stack) 
    {
    	return hasEnergy(stack, false) ? speed : 1.0F;
    }

    //look for hammer registry and determine destroy speed
    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull BlockState state) 
    {
    	if (hasEnergy(stack, false))
    	{
	        if (ExNihiloRegistries.CROOK_REGISTRY.isCrookable(state.getBlock()) && state.getBlock().getHarvestLevel(state) <= this.getTier().getLevel()) 
	        {
	            return (float) (this.speed * ExNihiloArsenalConfig.flux_crook_destroy_speed_modifier.get());
	        }
    	}
        return 1F;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) 
    {
        useEnergy(stack, false, attacker);
        return true;
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull BlockState state, @Nonnull BlockPos pos, LivingEntity entityLiving) 
    {
        if (Utils.isServerWorld(worldIn) && state.getDestroySpeed(worldIn, pos) != 0.0F)
        {
            useEnergy(stack, false, entityLiving);
        }
        return true;
    }

    //added reach because its a crook
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) 
    {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlotType.MAINHAND) 
        {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", getAttackDamage(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(ForgeMod.REACH_DISTANCE.get(), new AttributeModifier(UUID_TOOL_REACH , "Tool modifier", 1, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    @SuppressWarnings("deprecation")
	@Override
    public ActionResultType useOn(ItemUseContext context) 
    {
        ItemStack tool = context.getItemInHand();
        PlayerEntity player = context.getPlayer();
        if (player != null) 
        {
            World world = context.getLevel();
            if (player.isShiftKeyDown()) 
            {
                if (useEnergy(tool, true, player.abilities.instabuild)) 
                {
                    int r = REMOVE_RADIUS;
                    int r2 = r * r;
                    for (BlockPos pos : BlockPos.betweenClosed(context.getClickedPos().offset(-r, -r, -r), context.getClickedPos().offset(r, r, r))) 
                    {
                        if (pos.distSqr(context.getClickedPos()) < r2 && world.getBlockState(pos).getBlock().equals(FLUX_GLOW_AIR)) 
                        {
                            extinguishAir(world, player, pos, 0.3F);
                        }
                    }
                    return ActionResultType.SUCCESS;
                }
            } 
            else 
            {
                BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
                BlockState state = world.getBlockState(pos);
                if (state.getBlock().equals(FLUX_GLOW_AIR) && useEnergy(tool, false, player.abilities.instabuild)) 
                {
                    extinguishAir(world, player, pos, 0.5F);
                    return ActionResultType.SUCCESS;
                } 
                else if (state.isAir() && useEnergy(tool, true, player.abilities.instabuild)) 
                {
                    igniteAir(world, player, pos, 0.5F);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) 
    {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);

        if (!world.isClientSide() && isEmpowered(stack) && world.getGameTime() % 8 == 0) 
        {
            BlockPos pos = entity.blockPosition();
            if (world.isEmptyBlock(pos) && world.getRawBrightness(pos, world.getSkyDarken()) <= LOW_LIGHT_THRESHOLD && useEnergy(stack, true, entity)) 
            {
                igniteAir(world, null, pos, 0.3F);
            }
        }
    }

    public void igniteAir(World world, PlayerEntity player, BlockPos pos, float volume) 
    {
        world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.PLAYERS, volume, 1.0F);
        if (!world.isClientSide()) 
        {
            ((ServerWorld) world).sendParticles(RedstoneParticleData.REDSTONE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4, 0.25, 0.25, 0.25, 0);
        }
        world.setBlockAndUpdate(pos, FLUX_GLOW_AIR.defaultBlockState());
    }

    public void extinguishAir(World world, PlayerEntity player, BlockPos pos, float volume) 
    {
        world.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundCategory.PLAYERS, 0.5F, 1.0F);
        if (!world.isClientSide()) 
        {
            ((ServerWorld) world).sendParticles(RedstoneParticleData.REDSTONE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4, 0.25, 0.25, 0.25, 0);
        }
        world.setBlockAndUpdate(pos, AIR.defaultBlockState());
    }

    protected float getAttackDamage(ItemStack stack) 
    {
        return hasEnergy(stack, false) ? damage : 0.0F;
    }

    protected float getAttackSpeed(ItemStack stack) 
    {

        return attackSpeed;
    }

    @Override
    public int getExtract(ItemStack container) 
    {
        return extract;
    }

    @Override
    public int getReceive(ItemStack container) 
    {
        return receive;
    }

    @Override
    public int getMaxEnergyStored(ItemStack container) 
    {
        return getMaxStored(container, maxEnergy);
    }
}