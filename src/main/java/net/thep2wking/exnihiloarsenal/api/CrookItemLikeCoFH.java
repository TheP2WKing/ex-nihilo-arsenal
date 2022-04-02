package net.thep2wking.exnihiloarsenal.api;

import static cofh.lib.util.constants.Constants.TRUE;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import cofh.lib.item.ICoFHItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.NonNullList;
import novamachina.exnihilosequentia.api.ExNihiloRegistries;

//forked from PickaxeItemCoFH and CrookBaseItem
public class CrookItemLikeCoFH extends ToolItem implements ICoFHItem 
{
	protected BooleanSupplier showInGroups = TRUE;

    protected Supplier<ItemGroup> displayGroup;
    
    public CrookItemLikeCoFH(IItemTier tier, int attackDamageIn, float attackSpeedIn, Set<Block> effectiveOn, Properties builder) 
    {
		super(attackSpeedIn, attackDamageIn, tier, new HashSet<>(), builder);
	}
    
    public CrookItemLikeCoFH setDisplayGroup(Supplier<ItemGroup> displayGroup) 
    {
        this.displayGroup = displayGroup;
        return this;
    }

    public CrookItemLikeCoFH setShowInGroups(BooleanSupplier showInGroups) 
    {
        this.showInGroups = showInGroups;
        return this;
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) 
    {
        if (!showInGroups.getAsBoolean() || displayGroup != null && displayGroup.get() != null && displayGroup.get() != group) 
        {
            return;
        }
        super.fillItemCategory(group, items);
    }

    @Override
    public Collection<ItemGroup> getCreativeTabs() 
    {
        return displayGroup != null && displayGroup.get() != null ? Collections.singletonList(displayGroup.get()) : super.getCreativeTabs();
    }
    
    //look for harvestable blocks in crook registry
    @Override
    public boolean canHarvestBlock(@Nonnull ItemStack stack, @Nonnull BlockState state) 
    {
        return ExNihiloRegistries.CROOK_REGISTRY.isCrookable(state.getBlock()) || super.canHarvestBlock(stack, state);
    }
    
    //look for correct blocks in crook registry
    @Override
    public boolean isCorrectToolForDrops(@Nonnull final BlockState blockIn) 
    {
        if (ExNihiloRegistries.CROOK_REGISTRY.isCrookable(blockIn.getBlock())) 
        {
            return true;
        }
        return super.isCorrectToolForDrops(blockIn);
    }
}