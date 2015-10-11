package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public class GraphicsGetRotation extends DataRequest<EntityGraphics> {
	private float angle;
	public GraphicsGetRotation() {
		super(EntityGraphics.class);
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.GET_ROTATION;
	}
	@Override
	public <S> S getData(Class<S> dataClass) {
		return dataClass.cast(angle);
	}
	@Override
	public <S> void setData(S data) {
		angle = (Float) data;
	}
}