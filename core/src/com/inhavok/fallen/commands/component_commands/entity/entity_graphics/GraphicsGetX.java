package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public class GraphicsGetX extends DataRequest<EntityGraphics> {
	private float x;
	public GraphicsGetX() {
		super(EntityGraphics.class);
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.GET_X;
	}
	@Override
	public <S> S getData(Class<S> dataClass) {
		return dataClass.cast(x);
	}
	@Override
	public <S> void setData(S data) {
		x = (Float) data;
	}
}