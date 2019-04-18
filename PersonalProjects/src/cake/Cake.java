package cake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cake.additives.CakeFrosting;
import cake.additives.CakeLayer;
import cake.additives.CakeShape;
import cake.additives.CakeTopping;

public class Cake
{
	public static class CakeBuilder{
		private List<CakeLayer> cakeLayers = new ArrayList<>();
		private CakeFrosting cakeFrosting;
		private CakeShape cakeShape;
		private List<CakeTopping> cakeTopping = new ArrayList<>();
		
		public CakeBuilder(CakeLayer... cakeLayers) {
			this.cakeLayers.addAll(Arrays.asList(cakeLayers));
		}
	}
}
