import {
    Bar,
    BarChart,
    CartesianGrid,
    ResponsiveContainer,
    Tooltip,
    XAxis,
    YAxis,
} from "recharts";

import type {
    PayoutBucket,
    SimulationResult,
} from "../types/simulation.ts";

interface Props {
    result: SimulationResult;
}

const bucketLabels: Record<PayoutBucket, string> = {
    ZERO: "0x",
    UP_TO_0_2X: ">0–0.2x",
    UP_TO_0_5X: ">0.2–0.5x",
    UP_TO_1X: ">0.5–1x",
    UP_TO_2X: ">1–2x",
    UP_TO_5X: ">2–5x",
    UP_TO_10X: ">5–10x",
    UP_TO_50X: ">10–50x",
    UP_TO_100X: ">50–100x",
    UP_TO_500X: ">100–500x",
    OVER_500X: ">500x",
};

const bucketOrder: PayoutBucket[] = [
    "ZERO",
    "UP_TO_0_2X",
    "UP_TO_0_5X",
    "UP_TO_1X",
    "UP_TO_2X",
    "UP_TO_5X",
    "UP_TO_10X",
    "UP_TO_50X",
    "UP_TO_100X",
    "UP_TO_500X",
    "OVER_500X",
];

export function PayoutDistributionChart({
                                            result,
                                        }: Props) {

    const data = bucketOrder.map((bucket) => ({
        bucket: bucketLabels[bucket],
        cycles: result.payoutDistribution[bucket],
    }));

    return (
        <div style={{ width: "100%", height: 450 }}>
            <ResponsiveContainer>
                <BarChart
                    data={data}
                    margin={{
                        top: 20,
                        right: 20,
                        bottom: 40,
                        left: 30,
                    }}
                >
                    <CartesianGrid strokeDasharray="3 3" />

                    <XAxis
                        dataKey="bucket"
                        angle={-30}
                        textAnchor="end"
                        interval={0}
                    />

                    <YAxis />

                    <Tooltip
                        formatter={(value) =>
                            Number(value).toLocaleString()
                        }
                    />

                    <Bar
                        dataKey="cycles"
                        name="Paid spin cycles"
                        fill="#4f7cff"
                    />
                </BarChart>
            </ResponsiveContainer>
        </div>
    );
}